define(function (require, exports, module) {

    console.log("hostMeeting.js");

    require('mustache');
    require('jquery.mustache');

    var launchMeeting = require('launchMeeting');


    var btn_launchMeeting = $('#btn_launchMeeting'); //发起会议按钮
    var div_launchMeeting = $('#div_launchMeeting'); //显示发起会议页面的部分
    var div_launchMeeting_content = $('#div_launchMeeting_content'); //发起会议页面的主体内容
    var btn_close_launchMeeting = $('#btn_close_launchMeeting');  //关闭按钮

//    var input_new_meeting_topic = $('#input_new_meeting_topic'); //新会议标题输入框

    var btn_launch_new_meeting = $('#btn_launch_new_meeting'); //发起新会议的按钮

    var selected_ppt_id = undefined;


    $.Mustache.addFromDom('contentTemplate');

    //展开发起会议按钮事件
    btn_launchMeeting.click(function (e) {
        e.preventDefault();
        prepareDataforLaunchMeeting();
        show_div_launchMeeting();
    });

    //关闭按钮时间
    btn_close_launchMeeting.click(function (e) {
        e.preventDefault();
        hide_div_launchMeeting();
    })

    //真正发起会议的按钮时间
    div_launchMeeting_content.on('click', '#btn_launch_new_meeting', function (e) {
        e.preventDefault();
        var new_meeting_topic = $('#input_new_meeting_topic').val(); //获取会议标题输入
        if (new_meeting_topic.length == 0 || selected_ppt_id == undefined) {
            alert("请选择一个PPT并输入会议标题。");
        } else {
            alert(selected_ppt_id);
            alert(new_meeting_topic);
            launchMeeting.launchMeeting(selected_ppt_id, new_meeting_topic, uemail, token);
        }
    });

    //选择PPT时响应的事件
    div_launchMeeting_content.on('click', '.ppt_to_be_select', function (e) {
        e.preventDefault();
        $('.ppt_to_be_select').removeClass('active');
        $(this).addClass('active');
        selected_ppt_id = $(this).attr('pptId');
    });

    function prepareDataforLaunchMeeting() {
        div_launchMeeting_content.html("正在加载。。。稍等");
        $.ajax({
            type: 'GET',
            url: '/ppt/info_all',
            headers: {
                uemail: uemail,
                token: token
            },
            success: function (res, isSuccess) {
                var retcode = res.retcode;
                var data = res.data;
                switch (retcode) {
                    case 0:
                        var ppts = data;
                        var converted_ppts = [];
                        //筛选出已转换的ppt
                        $.each(ppts, function (index, ppt) {
                            if (ppt.isConverted) {
                                converted_ppts.push(ppt);
                            }
                        });
                        prepareUIforLaunchMeeting(ppts);
                        break;
                    default :
                        div_launchMeeting_content.html("加载失败，重新尝试");
                }
            },
            error: function (a, b) {

            }

        });

    }


    function prepareUIforLaunchMeeting(ppts) {
        if (!ppts) {
            div_launchMeeting_content.html("加载失败，重新尝试");
        }
        if (ppts.length == 0) {
            div_launchMeeting_content.html("<p>你暂时还没有已转换的幻灯哦！</p>");
        }
        var content = $.Mustache.render('contentTemplate', {ppts: ppts, uemail: uemail, token: token});
        div_launchMeeting_content.empty().html(content);
    }

    function hide_div_launchMeeting() {
        div_launchMeeting.addClass("hidden");
    }

    function show_div_launchMeeting() {
        div_launchMeeting.removeClass("hidden");
    }

});