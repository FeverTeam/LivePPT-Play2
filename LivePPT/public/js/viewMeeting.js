define(function(require, exports, module) {

	console.log("viewMeeting.js");

	var ws = new WebSocket("ws://localhost:9000/viewWebsocket");

	ws.onopen = function(){
		ws.send("1");
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