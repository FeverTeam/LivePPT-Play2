/**
 * Created with JetBrains WebStorm.
 * User: Johnnbug
 * Date: 13-8-10
 * Time: 下午6:26
 */
define(function(require, exports, module){
    require("backbone");
    require("bootstrap3");
    var signup = Backbone.View.extend({
        el: $("#signup"),
        //注册事件
        events: {
            "click #signupbtn"  : "signUp",
            "blur #email"       : "checkEmail",
            "blur #password"    : "checkPassword",
            "blur #tpassword"   : "checkTpassword",
            "blur #displayName" : "checkDisplayname"
        },

        inputState : [false,false,false,false],

        canSub : false,
        //判断是否各个input是否ready
        canSubmit : function(){
            for(var i = 0;i < 4;i ++){
                if(this.inputState[i] === false) {
                    this.canSub = false;
                    return false;
                }
            }
            this.canSub = true;
            return true;
        },
        //激活或者无效按钮
        disableButton : function(){
            //this.test() ;
            if(!this.canSub){
                $("#signupbtn").attr("disabled","disabled");
            } else{
                $("#signupbtn").removeAttr("disabled");
            }
        } ,
        //ajax 完成数据上传
        signUp : function(){
            //console.log(this) ;
            var email = $("#email").val();
            var password = $("#password").val();
            var displayname = $("#displayName").val();
            $.ajax({
                type: "POST",
                url: "/user/regist",
                dataType: "json",
                data: {"email":email,"password":password,"display":displayname },
                success: function (data){
                   //sign up success
                   console.log(data);
                   if(data.status == 9000){
                        window.location.href = "/";
                   }else{
                       console.log("error");
                   }
                }
            });
        },

        isBlank : function(input){
            if($(input).val() == "") return true;
            else return false;
        },

        test : function(){
            for(var i = 0;i < 4;i ++){
                console.log(this.inputState[i]);
            }
            console.log(this.canSub);

        },

        //以下代码待优化
        checkEmail : function(e){

            self = e.currentTarget;

            if(this.isBlank(self)) {

                $("#emailLabel").text("邮箱不能为空");
                $(self).parent().removeClass("has-success");
                $(self).parent().addClass("has-error");
                this.inputState[0] = false;

            }else {
                $("#emailLabel").text("您可以使用此邮箱");
                $(self).parent().removeClass("has-error");
                $(self).parent().addClass("has-success")
                this.inputState[0] = true;
            }

            this.canSubmit();

            this.disableButton();

        },

        checkPassword : function(e){
            self = e.currentTarget;
            if(this.isBlank(self)){
                $("#passwordLabel").text("密码不能为空");
                $(self).parent().removeClass("has-success");
                $(self).parent().addClass("has-error");
                this.inputState[1] = false;
            }else{
                $("#passwordLabel").text("成功");
                $(self).parent().removeClass("has-error");
                $(self).parent().addClass("has-success");
                this.inputState[1] = true;
            }
            this.canSubmit();
            this.disableButton();
        },

        checkTpassword : function(e){
            self = e.currentTarget;
            var ps = $("#password").val();
            var com_ps = $(self).val();
            if(ps === ""){

            }else{
                if(ps === com_ps){
                    $("#tpasswordLabel").text("成功");
                    $(self).parent().removeClass("has-error");
                    $(self).parent().addClass("has-success");
                    this.inputState[2] = true;
                }else{
                    $("#tpasswordLabel").text("与上次输入密码不匹配");
                    $(self).parent().removeClass("has-success");
                    $(self).parent().addClass("has-error");
                    this.inputState[2] = false;
                }
            }
            this.canSubmit();
            this.disableButton();
        },

        checkDisplayname : function(e){
            self = e.currentTarget;

            if(this.isBlank(self)) {

                $("#displayNameLabel").text("昵称不能为空");
                $(self).parent().removeClass("has-success");
                $(self).parent().addClass("has-error");
                this.inputState[3] = false;

            }else {
                $("#displayNameLabel").text("您可以使用此昵称");
                $(self).parent().removeClass("has-error");
                $(self).parent().addClass("has-success");
                this.inputState[3] = true;
            }
            this.canSubmit();
            this.disableButton();
        }

    });


    var app = new signup;

    $("#jz-left-eff").fadeIn(1000);

});