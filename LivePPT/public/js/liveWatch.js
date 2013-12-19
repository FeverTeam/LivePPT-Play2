define(function (require, exports, module) {
    console.log("liveWatch.js");

    //Dependencies
    require('jquery-knob');
    var constant = require('constant');

    //UI binding
    var ws_success_label = $('#ws-success-label'),
        ws_fail_label = $('ws-fail-label'),
        ws_recover_label = $('#ws-recover-label');  //连接状态标签
    var pageKnob = $('#pageKnob');  //页码指示器knob
    var stage_canvas = document.getElementById('stage_canvas'); //canvas

    //Data
    var wamp_uri = constant.wamp_uri;   //WAMP URI
    var global_session; //全局wamp连接
    var ppt_image_cache_array = [];     //PPT图片缓存数组

    //初始化
    $(function () {
        init();
    });

    function init() {
        if (!meetingId) {
            return;
        }

        //初始化pageKnob
        initPageKnob();

        //连接wamp服务器
        ab.connect(wamp_uri, on_wamp_success, on_wamp_error);
    }

    /**
     * wamp连接成功
     * @param session
     */
    function on_wamp_success(session) {
        global_session = session;  //保存session到全局
        global_session.call("page#currentIndex", meetingId)
            .then(
                on_call_currentIndex_success,
                on_call_currentIndex_error
            );

        setWSstatusLabel(0); //更新连接状态为已连接
    }

    /**
     * wamp连接失败
     * @param code
     * @param reason
     */
    function on_wamp_error(code, reason) {
        setWSstatusLabel(2);  //更新连接状态为连接断开正在重连
        console.log("code:" + code + "reason:" + reason);
    }

    /**
     * RPC获取页码成功
     * @param res
     */
    function on_call_currentIndex_success(res) {
        var json = JSON.parse(res);
        console.log(json);
        showIndexImg(json.pageIndex);
        global_session.subscribe(json.pageTopicUri, on_pubsub_pageTopic);
    }

    /**
     * RPC获取页码失败
     * @param error
     * @param desc
     */
    function on_call_currentIndex_error(error, desc) {
        console.log("error:" + error + " desc:" + desc);
    }

    /**
     * 响应页码topic的事件
     * @param topic
     * @param event
     */
    function on_pubsub_pageTopic(topic, event) {
//        console.log("topic:" + topic);
//        console.log("event:" + event);
        var pageId = event;
        showIndexImg(pageId);
    }

    /**
     * 显示指定页码的PPT图片
     * @param pageId
     */
    function showIndexImg(raw_pageId) {
        var pageId = parseInt(raw_pageId);

        var image = ppt_image_cache_array[pageId];
        if (image == undefined) {
            //未命中缓存
            console.log('page ' + pageId + ' not cached');
            ppt_image_cache_array[pageId] = new Image();
            ppt_image_cache_array[pageId].onload = function () {
                drawImageToCanvas(ppt_image_cache_array[pageId]); //绘制image到canvas
            }
            console.log('pageId:' + pageId);
            var image_uri = "/ppt/pageImage?pptId=" + pptId + "&page=" + pageId + "&uemail=" + uemail + "&token=" + token;
            ppt_image_cache_array[pageId].src = image_uri;
        } else {
            //命中缓存
            console.log('page' + pageId + ' cache hit.');
            drawImageToCanvas(image); //绘制image到canvas
        }

        preloadImgs(pageId);

        //更新page knob
        pageKnob.val(pageId).trigger('change');
    }

    /**
     * 预载前后页码的PPT图片
     * @param pageId
     */
    function preloadImgs(pageId) {

        var preload_img_count = constant.preload_img_count;
        var i;
        for (i = pageId - preload_img_count; i <= pageId + preload_img_count; i++) {
            if (i == pageId) {
                continue;
            }
            var isInRange = (i >= 1 && i <= pageCount) ? true : false;

            if (!isInRange) {
                //超出页码范围
                continue;
            }
            if (ppt_image_cache_array[i]) {
                //页码的图片已缓存
                continue;
            }

            ppt_image_cache_array[i] = new Image();
            var image_uri = "/ppt/pageImage?pptId=" + pptId + "&page=" + i + "&uemail=" + uemail + "&token=" + token;
            ppt_image_cache_array[i].src = image_uri;
        }
    }

    /**
     * 绘制Image对象到主Canvas
     * @param image
     */
    function drawImageToCanvas(image) {

        //设置canvas的长宽
        stage_canvas.width = image.width;
        stage_canvas.height = image.height;

        //将图片绘制到canvas上
        var canvas_context = stage_canvas.getContext('2d');
        canvas_context.drawImage(image, 0, 0);
    }

    /**
     * 设置WAMP连接状态标签
     * @param status
     */
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

    /**
     * 初始化Knob
     */
    function initPageKnob() {
        pageKnob.knob({
            min: 0,
            max: pageCount,
            readOnly: true
        });
    }


});