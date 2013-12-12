define(function(require, exports, module) {

	console.log("signup.js");

    require('hmac');  // 加载CryptoJS
	require('aes');
	require('ecb');


    var signup = function(email, password, displayName){
        var seed = "1234567890123456";

        var key = CryptoJS.enc.Latin1.parse(seed);
		var iv  = CryptoJS.enc.Latin1.parse(seed);
    	var encrypted = CryptoJS.AES.encrypt(password, key, { iv:iv, mode: CryptoJS.mode.ECB});
    	var encryptedText = encrypted.ciphertext+"";

        $.ajax({
            url: '/user/register',
            type: 'post',
            data: {
                uemail: email,
                password: encryptedText,
                seed: seed,
				displayname: displayName
            },
            success: function(res, status){
                console.log(res);
                switch (res.retcode) {
                    case 0:
                        //登录成功
                        location.href = '/loginSuccess?uemail='+email+'&token='+res.data.token+'&callbackUrl='+window.location.href;
                        break;
                    default :
                        alert(res.retcode + res.message);
                }

//                if (res.retcode == 0)

//                else if (res.retcode == -202) {
//                    showmsg('此用户不存在');
//                }
//                else if (res.retcode == -203) {
//                    showmsg('账号或密码错误');
//                }
//                else {
//                    showmsg('登录失败！');
//                }
            },
            error: function(res, status){
                showmsg('网络错误');
            }
        });
    }


    //exports
    exports.signup = signup;

//	var alertBox = $("#alertBox");
//
//	var uemail = "";
//
//	$('.btn-signup-submit').on('click', function(e){
//		e.preventDefault();
//		uemail = $('#inputEmail').val();
//		var psw = $('#inputPassword').val();
//
//		var seed = "1234567890123456";
//
//		var key = CryptoJS.enc.Latin1.parse(seed);
//		var iv  = CryptoJS.enc.Latin1.parse(seed);
//    	var encrypted = CryptoJS.AES.encrypt(psw, key, { iv:iv, mode: CryptoJS.mode.ECB});
//    	var encryptedText = encrypted.ciphertext+"";
//
//    	console.log(encrypted);
//    	console.log("encryptedText:"+encryptedText);
//
//		//通过Ajax发出注册请求
//		$.ajax({
//			url: '/user/register',
//			type: 'post',
//			dataType: 'json',
//			data: {
//				uemail: uemail,
//				password: encryptedText,
//				seed: seed,
//				displayname: $('#inputDisplayName').val()
//			},
//			success: function(res, status){
//				console.log(res);
//				if (res.retcode == 0){
//					var url = '/loginSuccess?';
//					url += 'uemail=' + uemail;
//					url += '&token=' + res.data.token;
//					url += '&callbackUrl=/myppt';
//					window.location.href = url;
//				}
//				else {
//					showmsg('注册失败');
//				}
//			},
//			error: function(res, status){
//				showmsg('网络错误');
//			}
//		});
//	});

});