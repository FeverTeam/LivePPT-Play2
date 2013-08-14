define(function(require, exports, module){
    require("backbone");
    require("bootstrap3");
    var login = Backbone.View.extend({
        el: $("body"),
        //注册事件
        events: {
            "click #loginBtn"  : "login",
            "click .closeBtn" : "toIndex"
        },
        //ajax 完成数据上传
        login: function(){
            $.ajax({
                type: "GET",
                url: "/user/login",
                dataType: "json",
                data: {
                    email: $('#inputEmail').val(),
                    password: $('#inputPassword').val()
                },
                success: function(data){
                    console.log(data);
                    if(data.statusCode == 9000){
                        window.location.href = "/myppt";
                    }else if(data.statusCode == 1101){
                        $("#loginMessage").html("email不存在");
                        $("#loginMessage").css("display", "inline");
                    }else{
                        $("#loginMessage").html("密码错误");
                        $("#loginMessage").css("display", "inline");
                    }
                },
                error: function(status){
                    $("#loginMessage").html("网络错误");
                    $("#loginMessage").css("display", "inline");
                }
            });
        },
        /*跳转回主页*/
        toIndex: function(){
            window.location.href = "/";
        }
    });

    var app = new login;
});