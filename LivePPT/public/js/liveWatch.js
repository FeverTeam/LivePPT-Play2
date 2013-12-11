define(function (require, exports, module) {
    console.log("liveWatch.js");

//  require('aab');
    require('jquery-knob');

    //wamp
    var wamp_uri = "ws://localhost:9000/wamp";
//    var wamp_uri = "ws://cloudslides.net:9000/wamp";

    var global_session;

    //连接状态标签
    var ws_success_label = $('#ws-success-label'),
        ws_fail_label = $('ws-fail-label'),
        ws_recover_label = $('#ws-recover-label');

    //页码指示器knob
    var pageKnob = $('#pageKnob');

    init();

    function init(){
        if (!meetingId){
            return;
        }

        //初始化pageKnob
        initPageKnob();
        //连接wamp服务器
        ab.connect(wamp_uri, on_wamp_success, on_wamp_error);
    }





    function on_wamp_success(session) {
        global_session = session;  //保存session到全局
        global_session.call("page#currentIndex", meetingId)
            .then(
                on_call_currentIndex_success,
                on_call_currentIndex_error
            );

        setWSstatusLabel(0); //更新连接状态为已连接
    }

    function on_wamp_error(code, reason) {
        setWSstatusLabel(2);  //更新连接状态为连接断开正在重连
        console.log("code:" + code + "reason:" + reason);
    }

    function on_call_currentIndex_success(res) {
        var json = JSON.parse(res);
        console.log(json);
        showIndexImg(json.pageIndex);
        global_session.subscribe(json.pageTopicUri, on_pubsub_pageTopic);
    }

    function on_call_currentIndex_error(error, desc) {
        console.log("error:" + error + " desc:" + desc);
    }

    function on_pubsub_pageTopic(topic, event) {
        console.log("topic:"+topic);
        console.log("event:"+event);
        var pageId = event;
        showIndexImg(pageId);
    }


    function showIndexImg(pageId) {
        pageId = parseInt(pageId);

        //更新page knob
        pageKnob.val(pageId).trigger('change');

//        fetchIndexImg(pptId, pageId);

        // preFetch
//        for (var count = 1; count < 3; count++){
//            fetchIndexImg(pageid-1);
//            fetchIndexImg(pageid+1);
//        }

        $('div#pageImgPool img').addClass('hidden');
        $('div#pageImgPool #page' + pageId).removeClass('hidden');
    }


    function setWSstatusLabel(status) {
        $('span.label').addClass('hidden');
        switch (status) {
            case 0:
                ws_success_label.removeClass('hidden');  //"ws-success-label"
                break;
            case 1:
                ws_fail_label.removeClass('hidden');  //"ws-fail-label"
                break;
            case 2:
                ws_recover_label.removeClass('hidden');  //"ws-recover-label"
                break;
        }
    }

    function initPageKnob() {
        pageKnob.knob({
            min: 0,
            max: pageCount,
            readOnly: true
        });
    }


});