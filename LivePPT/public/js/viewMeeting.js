define(function(require, exports, module) {
	require('jquery-knob');
	require('cookies')
	console.log("viewMeeting.js");

//	var ws_address = "ws://localhost:9000/viewWebsocket";
	var ws_address = "ws://cloudslides.net:9000/viewWebsocket";

	var dataDiv = $('div#dataDiv');
	var meetingId = dataDiv.attr('meetingid');
	var pptId = dataDiv.attr('pptId');
	var pageCount = dataDiv.attr('pageCount');

	var pageKnob = $('#pageKnob');

	var webSocket = undefined;

	var ws_success_label = $('#ws-success-label'),
	ws_fail_label = $('ws-fail-label'),
	ws_recover_label = $('#ws-recover-label');

	
	init(meetingId);
	

	function init(meetingId){
		ws = initWebSocket(meetingId);
		initPageKnob();
	}


	function initWebSocket(meetingId){
		var ws = new WebSocket(ws_address);
		console.log("WebSocket address:"+ws_address);
		ws.onopen = function(){
			ws.send(meetingId);
		
			console.log('WebSocket Connection open [meetingId='+meetingId+']');

			setWSstatusLabel(0);

			showIndexImg(pptId, 1);
		};

		ws.onclose = function(){ 
			// websocket is closed.
			console.log("WebSocket Connection is closed...");
			setWSstatusLabel(2);
			webSocket = initWebSocket(meetingId);
		};

		ws.onmessage = function (evt){
			var data = evt.data.split("-");
			var pptid = data[0];
			var pageid = data[1];
			console.log(evt);
			console.log(pptid + "--"+pageid);
			// $('#currentImg').attr('src','/getpptpage?pptid='+pptid+'&pageid='+pageid);
			showIndexImg(pptid, pageid);

			pageKnob.val(pageid).trigger('change');
			
		};

		return ws;

	}

	function setWSstatusLabel(status){
		$('span.label').addClass('hide');
		switch (status) {
			case 0:
				ws_success_label.removeClass('hide');
				break;
			case 1:
				ws_fail_label.removeClass('hide');
				break;
			case 2:
				ws_recover_label.removeClass('hide');
				break;
		}
	}

	function showIndexImg(pptId, pageid){
		pageid = parseInt(pageid);

		fetchIndexImg(pptId, pageid);

		// preFetch
		for (var count=1;count<3; count++){
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
					+'" class="page hide img-polaroid" id="page'+pageId+'"/>');
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