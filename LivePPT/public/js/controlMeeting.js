define(function(require, exports, module) {
	require('jquery-knob');
	require('cookies');

	console.log("contrlMeeting.js");

	//wamp
	// var wamp_uri = "ws://localhost:9000/wamp";
	var wamp_uri = "ws://cloudslides.net:9000/wamp";
	var global_session;

	var dataDiv = $('#datadiv');
	var meetingId = parseInt(dataDiv.attr('meetingid'));
	var pptId = dataDiv.attr('pptid');
	var pageCount = dataDiv.attr('pageCount');

	var btnPrePage = $('button#prePage');
	var btnNextPage = $('button#nextPage');

	var pageKnob = $('#pageKnob');

	var currentPageIndex = 1;

	var pptCarousel = $('.carousel#pptCarousel');

	//from cookie
	var uemail = $.cookie('uemail');
	var token = $.cookie('token');

	//初始化
	$(function(){
		initPageKnob();
		setKnob(currentPageIndex);

		//连接wamp服务器
		ab.connect(wamp_uri, on_wamp_success, on_wamp_error);
	});

	function on_wamp_success(session){
		global_session = session;
		console.log("ok");
	}

	function on_wamp_error(code, desc){
		console.log("code:"+code+" desc:"+desc);
	}
		

	$('.btn#prePage').on('click', function(e){
	    pptCarousel.carousel('prev');
    });

	$('.btn#nextPage').on('click', function(e){
	    pptCarousel.carousel('next');
    });



	function setCurrentImg(index){
		// currentImg.attr('src', '/getpptpage?pptid='+pptId+'&pageid='+index);
		var pre = index-1, next = index+1;
		$('.page#'+index).removeClass('hide');

		$('.page#'+pre).addClass('hide');
		$('.page#'+next).addClass('hide');

		setPagination(index);

		setKnob(index);
	}

	pptCarousel.on('slid', function(e){
        currentPageIndex = $(".active", e.target).index();
        setKnob(currentPageIndex+1);
        setMeetingPage(currentPageIndex+1);
        setPagination(currentPageIndex+1);

    });

	function setMeetingPage(currentPageIndex){
		if (global_session==null){
			ab.connect(wamp_uri, on_wamp_success, on_wamp_error);  //重新连接wamp服务器
		}
		global_session.call("page#set", uemail, token, meetingId, currentPageIndex);

	}

	function setPagination(currentPageIndex){
		$('.pagination li').removeClass('active');
		$('.pagination li#page'+currentPageIndex).addClass('active');
	}

	function initPageKnob(){
		pageKnob.knob({
			min: 0,
			max: pageCount,
			readOnly: true
		});
	}

	/**
	 * 列表分页增加设置PPT页面跳转功能
	 */
	$('.pagination li').click(function () {
//		var old = currentImg.data('currentIndex');
		var news = $(this).data("id");

		pptCarousel.carousel(news-1);
		return false;
	})

	function setKnob(index){
	    pageKnob.val(index).trigger('change');
	}
});