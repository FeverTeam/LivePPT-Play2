define(function(require, exports, module) {

	console.log("login.js");

	$('.btn-login-submit').on('click', function(e){
		e.preventDefault();
		alert("submit");
	});

});