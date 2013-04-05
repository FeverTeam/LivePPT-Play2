define(function(require, exports, module) {

	console.log("signup.js");

	$('.btn-signup-submit').on('click', function(e){
		e.preventDefault();

		$.ajax({
			url: '/signdown',
			type: 'post',			
			data: {
				email: $('#inputEmail').val(),
				password: $('#inputPassword').val(),
				displayname: $('#inputDisplayName').val()
			},
			success: function(res){
				console.log(res);
			},
			error: function(res){
				console.log(res);
			}
		});
		console.log("signup");
	});

});