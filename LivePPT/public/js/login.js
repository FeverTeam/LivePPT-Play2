define(function(require, exports, module) {

	console.log("login.js");

	var alertBox = $("#alertBox");
	$('.btn-login-submit').on('click', function(e){
		e.preventDefault();
		var psw = $('input[name=password]').val();
		var hash = CryptoJS.HmacSHA1(psw, "Message");
		$.ajax({
			url: '/user/login',
			type: 'post',
			data: {
				uemail: $('input[name=email]').val(),
				password: hash.toString(),
				seed: "Message"
			},
			success: function(res, status){
				console.log('登陆成功');
				location.href = '/loginSuccess?uemail='+$('input[name=email]').val()+'&token='+res.data.token+'&callbackUrl='+window.location.href;
			},
			error: function(res, status){
				alert('网络错误')
			}
		});
	});

});