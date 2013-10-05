define(function(require, exports, module) {

	console.log("signup.js");
	require('aes');
	require('ecb');

	var alertBox = $("#alertBox");

	$('.btn-signup-submit').on('click', function(e){
		e.preventDefault();
		var psw = $('#inputPassword').val();

		var seed = "1234567890123456";

		var key = CryptoJS.enc.Latin1.parse(seed);
		var iv  = CryptoJS.enc.Latin1.parse(seed);
    	var encrypted = CryptoJS.AES.encrypt(psw, key, { iv:iv, mode: CryptoJS.mode.ECB});
    	var encryptedText = encrypted.ciphertext+"";

    	console.log(encrypted);
    	console.log("encryptedText:"+encryptedText);

		//通过Ajax发出注册请求
		$.ajax({
			url: '/user/register',
			type: 'post',
			dataType: 'json',
			data: {
				uemail: $('#inputEmail').val(),
				password: encryptedText,
				seed: seed,
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