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
				console.log(res);
				if (res.retcode == 0)
					location.href = '/loginSuccess?uemail='+$('input[name=email]').val()+'&token='+res.data.token+'&callbackUrl='+window.location.href;
				else if (res.retcode == -202) {
					showmsg('此用户不存在');				
				}
				else if (res.retcode == -203) {
					showmsg('账号或密码错误');					
				}
				else {
					showmsg('登录失败！');
				}
			},
			error: function(res, status){
				showmsg('网络错误');
			}
		});
	});
});