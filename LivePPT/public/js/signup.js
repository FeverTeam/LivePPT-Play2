define(function(require, exports, module) {

	console.log("signup.js");

	var alertBox = $("#alertBox");

	$('.btn-signup-submit').on('click', function(e){
		e.preventDefault();

		$.ajax({
			url: '/signup',
			type: 'post',			
			data: {
				email: $('#inputEmail').val(),
				password: $('#inputPassword').val(),
				displayname: $('#inputDisplayName').val()
			},
			success: function(res){
				console.log(res);
				$("#alertBox span").html(res);
				alertBox.alert();

			},
			error: function(res){
				console.log(res);
			}
		});
		console.log("signup");
	});

});