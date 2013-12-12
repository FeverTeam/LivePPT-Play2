define(function (require, exports, module) {

    console.log("index.js");

    var loginMod = require("login");
    var signupMod = require("signup");

    var nav_login = $("#nav_login"); //导航栏登录按钮
    var nav_signup = $("#nav_signup");//导航栏注册按钮

    var div_login = $('#div_login'); //登录页面
    var div_signup = $('#div_signup'); //注册页面

    var btn_close_login_signup = $("#btn-close-login-signup");  //关闭按钮

    var button_login = $('#button_login'); //登录按钮
    var button_signup = $('#button_signup'); //登录按钮

    var input_login_email = $('#input_login_email');
    var input_login_password = $('#input_login_password');

    var input_signup_email = $('#input_signup_email');
    var input_signup_password = $('#input_signup_password');
    var input_signup_displayName = $('#input_signup_displayName');

    var main_btn_signup = $('#main_btn_signup');


    //导航栏登录按钮事件
    nav_login.click(function (e) {
        e.preventDefault();
        div_signup.addClass('hidden');
        div_login.removeClass('hidden');

        btn_close_login_signup.removeClass('hidden');  //显示关闭按钮
    });

    //导航栏注册按钮事件
    nav_signup.click(function (e) {
        e.preventDefault();
        div_login.addClass('hidden');
        div_signup.removeClass('hidden');

        btn_close_login_signup.removeClass('hidden');  //显示关闭按钮
    });

    //关闭按钮事件
    btn_close_login_signup.click(function (e) {
        e.preventDefault();
        //隐藏登录和注册页面
        div_login.addClass('hidden');
        div_signup.addClass('hidden');

        btn_close_login_signup.addClass('hidden');  //隐藏关闭按钮
    });

    //登录按钮事件
    button_login.click(function (e) {
        e.preventDefault();

        var email = input_login_email.val();
        var password = input_login_password.val();

        loginMod.login(email, password);

    });


    //注册按钮事件
    button_signup.click(function (e) {
        e.preventDefault();
        var email = input_signup_email.val();
        var password = input_signup_password.val();
        var displayName = input_signup_displayName.val();
        signupMod.signup(email, password, displayName);
    });

    //主注册按钮事件
    main_btn_signup.click(function(e){
        nav_signup.click();
    });

});