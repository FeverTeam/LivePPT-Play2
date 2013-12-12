define(function (require, exports, module) {

    console.log("watchMeetings.js");

    var joinMeeting = require('joinMeeting');

//    require('mustache');
//    require('jquery.mustache');

    var btn_show_joinMeeting = $('#btn_joinMeeting'); //展开加入会议的按钮

    var div_joinMeeting = $('#div_joinMeeting'); //展示加入会议页面的部分

    var btn_close_joinMeeting = $('#btn_close_joinMeeting'); //关闭加入会议页面的按钮

    var div_joinMeeting_content = $('#div_joinMeeting_content'); //加入会议页面的具体内容

    var input_join_meeting_id = $('#input_join_meeting'); //加入会议的ID输入框

    var btn_join_meeting = $('#btn_join_meeting'); //真正加入观看会议


    //展开加入会议的按钮事件
    btn_show_joinMeeting.click(function (e) {
        e.preventDefault();
        show_div_launchMeeting();
    });

    //关闭加入会议页面的按钮事件
    btn_close_joinMeeting.click(function (e) {
        e.preventDefault();
        hide_div_launchMeeting();
    });

    //真正加入观看会议时间
    btn_join_meeting.click(function(e){
        e.preventDefault();
        var meetingId = input_join_meeting_id.val();
        if (meetingId.length==0){
            return;
        }
        if (isNaN(meetingId)){
            //不是数字
            alert("只可以输入数字哦!");
        } else {
            //是数字
            joinMeeting.joinMeeting(meetingId, uemail, token);
        }
    });


    function hide_div_launchMeeting() {
        div_joinMeeting.addClass("hidden");
    }

    function show_div_launchMeeting() {
        div_joinMeeting.removeClass("hidden");
    }


});