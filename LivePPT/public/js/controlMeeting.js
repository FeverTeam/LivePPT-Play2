define(function(require, exports, module) {
	require('jquery-knob');
	require('cookies');

	console.log("contrlMeeting.js");

	var currentImg = $('#img-current-page');
	var dataDiv = $('#datadiv');
	var meetingId = dataDiv.attr('meetingid');
	var pptId = dataDiv.attr('pptid');
	var pageCount = dataDiv.attr('pageCount');

	var btnPrePage = $('button#prePage');
	var btnNextPage = $('button#nextPage');

	var pageKnob = $('#pageKnob');

	currentImg.data('currentIndex', 1);
	// currentImg.attr('src', '/getpptpage?pptid='+pptId+'&pageid='+currentImg.data('currentIndex'));
	setCurrentImg(1);

	initPageKnob();


	//上一页
	btnPrePage.on('click', function(e){
		var index = setPreImg();
		setMeetingPage(index);

	});

	//下一页
	btnNextPage.on('click', function(e){
		var index = setNextImg();
		setMeetingPage(index);
	});

	function setCurrentImg(index){
		// currentImg.attr('src', '/getpptpage?pptid='+pptId+'&pageid='+index);
		var pre = index-1, next = index+1;
		$('.page#'+index).removeClass('hide');

		$('.page#'+pre).addClass('hide');
		$('.page#'+next).addClass('hide');

		setPagination(index);

		pageKnob.val(index).trigger('change');
	}

	function setPreImg(){
		var index = currentImg.data('currentIndex')
		index--;
		if (index<1) {index=1;}
		currentImg.data('currentIndex', index);
		setCurrentImg(index);
		return index;	
	}

	function setNextImg(){
		var index = currentImg.data('currentIndex')
		index++;
		if (index>pageCount) {index=pageCount;}
		currentImg.data('currentIndex', index);
		setCurrentImg(index);
		return index;
	}

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
			min:0,
			max: pageCount,
			readOnly: true
		});
	}

	/**
	 * 列表分页增加设置PPT页面跳转功能
	 */
	$('.pagination li').click(function () {
		var old = currentImg.data('currentIndex');
		var news = $(this).data("id");

		$('.page#'+old).addClass('hide');
		$('.page#'+news).removeClass('hide');
		pageKnob.val(news).trigger('change');

		currentImg.data('currentIndex', news);
		setPagination(news);
		setMeetingPage(news);
		return false;
	})
});