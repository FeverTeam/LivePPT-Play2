define(function (require, exports, module) {

    var joinMeeting = function(meetingId, uemail, token) {
        $.ajax({
            type: 'POST',
            url: '/meeting/join',
            headers: {
                uemail: uemail,
                token: token
            },
            data: {
                meetingId: meetingId
            },
            success: function (res, isSuccess) {
                console.log(res);
                switch (res.retcode) {
                    case 0:
                        //成功加入会议
                        location = location; //刷新当前页面
                        break
                    default :
                        alert("无法加入会议！（" + res.retcode + " " + res.message + ")");
                }
            },
            error: function (a, b) {
                console.log(a);
                console.log(b);
            }
        });

    }

    exports.joinMeeting = joinMeeting;

});