define(function (require, exports, module) {

    var launchMeeting = function (pptId, topic, uemail, token) {

        $.ajax({
            type: 'POST',
            url: '/meeting/create',
            headers: {
                uemail: uemail,
                token: token
            },
            data: {
                pptId: pptId,
                topic: topic
            },
            success: function (res, isSuccess) {
                console.log(res);
                switch (res.retcode) {
                    case 0:
                        location = location; //刷新当前页面
                        break
                    default :
                        alert("无法建立会议！（" + res.retcode + " " + res.message + ")");
                }
            },
            error: function (a, b) {
                console.log(a);
                console.log(b);
            }
        });
    };

    module.exports = {
        launchMeeting: launchMeeting
    };


});