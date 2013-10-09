define(function(require, exports, module) {
	require('jquery-knob');
	require('cookies');

	console.log("contrlMeeting.js");

	var dataDiv = $('#datadiv');
	var meetingId = dataDiv.attr('meetingid');
	var pptId = dataDiv.attr('pptid');
	var pageCount = dataDiv.attr('pageCount');

	var btnPrePage = $('button#prePage');
	var btnNextPage = $('button#nextPage');

	var pageKnob = $('#pageKnob');

	var currentPageIndex = 1;

	var pptCarousel = $('.carousel#pptCarousel');



	initPageKnob();
	setKnob(currentPageIndex);

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
		$.ajax({
			type: 'POST',
			url: '/meeting/setPage',
			data: {
				meetingId: meetingId,
				pageIndex: currentPageIndex
			},
			success: function(res, status){
				if (!res.retcode) {
					console.log("Set remote page index:"+currentPageIndex);
				};
			},
			headers: {
				'uemail': $.cookie('uemail'),
				'token': $.cookie('token')
			}
		})
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