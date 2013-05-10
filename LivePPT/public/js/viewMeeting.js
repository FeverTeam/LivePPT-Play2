define(function(require, exports, module) {

	console.log("viewMeeting.js");

	var ws_address = "ws://localhost:9000/viewWebsocket";
	// var ws_address = "ws://live-ppt.com:9000/viewWebsocket";

	var meetingId = $('div#dataDiv').attr('meetingid');

	var ws = new WebSocket(ws_address);
	console.log("WebSocket address:"+ws_address);

	ws.onopen = function(){
		ws.send(meetingId);
		
		console.log('WebSocket Connection open [meetingId='+meetingId+']');
	};

	ws.onclose = function(){ 
		// websocket is closed.
		console.log("WebSocket Connection is closed..."); 
	};

	ws.onmessage = function (evt){
		var data = evt.data.split("-");
		var pptid = data[0];
		var pageid = data[1];
		console.log(evt);
		console.log(pptid + "--"+pageid);
		$('#currentImg').attr('src','/getpptpage?pptid='+pptid+'&pageid='+pageid);		
	};
	

});