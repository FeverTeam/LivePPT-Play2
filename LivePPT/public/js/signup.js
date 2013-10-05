define(function(require, exports, module) {

	console.log("signup.js");
	require('aes');
	var alertBox = $("#alertBox");

	$('.btn-signup-submit').on('click', function(e){
		e.preventDefault();
		var psw = $('#inputPassword').val();
		var hash = CryptoJS.AES.encrypt(psw, "0123456789123456");;
		//通过Ajax发出注册请求
		$.ajax({
			url: '/user/register',
			type: 'post',
			dataType: 'json',
			data: {
				uemail: $('#inputEmail').val(),
				password: hash.toString(),
				seed: "0123456789123456",
				displayname: $('#inputDisplayName').val()
			},
			success: function(res, status){
				console.log(res);
				if (res.retcode == 0)
					window.location.href = '/myppt';
				else {
					showmsg('注册失败');
				}
			},
			error: function(res, status){
				showmsg('网络错误');
			}
		});
	});

});