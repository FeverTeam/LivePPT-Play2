define(function(require, exports, module) {
	require('jquery-knob');
	require('cookies');
	// require('autobahn');  //autobahn for wamp

	console.log("viewMeeting.js");

	//wamp
	var wamp_uri = "ws://localhost:9000/wamp";
	// var wamp_uri = "ws://cloudslides.net:9000/wamp";

	var global_session;

	var dataDiv = $('div#dataDiv');
	var meetingId = dataDiv.attr('meetingid');
	var pptId = dataDiv.attr('pptId');
	var pageCount = dataDiv.attr('pageCount');

	var pageKnob = $('#pageKnob');

	//连接状态标签
	var ws_success_label = $('#ws-success-label'),
	ws_fail_label = $('ws-fail-label'),
	ws_recover_label = $('#ws-recover-label');

	//初始化	
	$(function(){
		//初始化pageKnob
		initPageKnob();

		//连接wamp服务器
		ab.connect(wamp_uri, on_wamp_success, on_wamp_error);
	});

	function on_wamp_success(session){		
		global_session = session;  //保存session到全局
		global_session.call("page#currentIndex", meetingId)
			.then(
				on_call_currentIndex_success,
				on_call_currentIndex_error
			);

		setWSstatusLabel(0); //更新连接状态为已连接
	}

	function on_wamp_error(code, reason){
		setWSstatusLabel(2);  //更新连接状态为连接断开正在重连
		console.log("code:"+code+"reason:"+reason);
	}

	function on_pubsub_pageTopic(topic, event) {
		var pageId = event;
 	 	showIndexImg(pptId, pageId);
	}

	function on_call_currentIndex_success(res){
		var json = JSON.parse(res);		
		showIndexImg(pptId, json.pageIndex);
		global_session.subscribe(json.pageTopicUri, on_pubsub_pageTopic);
	}

	function on_call_currentIndex_error(error, desc){
		console.log("error:"+error+" desc:"+desc);		
	}
	
	function setWSstatusLabel(status){
		$('span.label').addClass('hide');
		switch (status) {
			case 0:
				ws_success_label.removeClass('hide');  //"ws-success-label"
				break;
			case 1:
				ws_fail_label.removeClass('hide');  //"ws-fail-label"
				break;
			case 2:
				ws_recover_label.removeClass('hide');  //"ws-recover-label"
				break;
		}
	}

	function showIndexImg(pptId, pageid){
		//更新page knob
		pageKnob.val(pageid).trigger('change');

		pageid = parseInt(pageid);

		fetchIndexImg(pptId, pageid);

		// preFetch
		for (var count = 1; count < 3; count++){
			fetchIndexImg(pptId, pageid-1);
			fetchIndexImg(pptId, pageid+1);
		}
		$('div#pageImgPool img').addClass('hide');
		$('div#pageImgPool #page'+pageid).removeClass('hide');
	}

	function fetchIndexImg(pptId, pageId){		
		if (pageId>0) {
			var img = $('div#pageImgPool #page'+pageId);
			if (img.length<1){
				$('div#pageImgPool').append('<img src="/ppt/pageImage?pptId='+pptId+'&page='+pageId
					+'&uemail='+$.cookie('uemail')+'&token='+$.cookie('token')
					+'" class="page hide img-responsive" id="page'+pageId+'"  style="MARGIN-RIGHT: auto;MARGIN-LEFT: auto; "/>');
			}
		}
	}

	function initPageKnob(){
		pageKnob.knob({
			min:0,
			max: pageCount,
			readOnly: true
		});
	}

});