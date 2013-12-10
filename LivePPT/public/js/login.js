define(function(require, exports, module) {

	console.log("login.js");

    require('hmac');  // 加载CryptoJS



    var login = function(email, password){
        var seed="Message";
        var hash = CryptoJS.HmacSHA1(password, seed);
        $.ajax({
            url: '/user/login',
            type: 'post',
            data: {
                uemail: email,
                password: hash.toString(),
                seed: seed
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
    exports.login = login;

//	var alertBox = $("#alertBox");
//	$('.btn-login-submit').on('click', function(e){
//		e.preventDefault();
//		var psw = $('input[name=password]').val();
//		var hash = CryptoJS.HmacSHA1(psw, "Message");

//	});
});