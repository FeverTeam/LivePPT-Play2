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
                    /*后台返回的data似乎只有status，待修改*/
                },
                error: function(status){
                    console.log("error");
                    /*待修改*/
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