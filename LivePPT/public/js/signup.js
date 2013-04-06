define(function(require, exports, module) {

	console.log("signup.js");

	var alertBox = $("#alertBox");

	alertBox.hide();

	$('.btn-signup-submit').on('click', function(e){
		e.preventDefault();

		//通过Ajax发出注册请求
		$.ajax({
			url: '/signup',
			type: 'post',
			dataType: 'json',
			data: {
				email: $('#inputEmail').val(),
				password: $('#inputPassword').val(),
				displayname: $('#inputDisplayName').val()
			},
			success: function(res, status){
				if (res.isSuccess){
					//注册成功，跳转页面
					window.location.href = '/myppt';
				} else {
					//注册不成功，显示错误信息
					$("#alertBox span").html(res.message);
					alertBox.show();
				}
			},
			error: function(res){
				$("#alertBox span").html("网络错误？");
				alertBox.show();
			}
		});
	});

});