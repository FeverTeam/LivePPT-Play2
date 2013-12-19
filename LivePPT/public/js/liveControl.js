define(function (require, exports, module) {
    console.log("liveControl.js");

    //Dependencies
    require('jquery-knob');
    var constant = require('constant');
    var imageUtils = require('tools/imageUtils');

    //UI

    // 页码指示器knob
    var pageKnob = $('#pageKnob');

    //上一页下一页按钮
    var btn_prePage = $('#btn_prePage');
    var btn_nextPage = $('#btn_nextPage');

    //Data

    //wamp
    var wamp_uri = constant.wamp_uri;

    //全局的wamp连接
    var global_session;

    //全局页码
    var globalPageIndex = 1;


    $(function () {
        console.log('wamp_uri: ' + wamp_uri);
        init();
    });

    /**
     * 初始化
     */
    function init() {
        if (!meetingId) {
            return;
        }

        //初始化pageKnob
        initPageKnob();

        //连接wamp服务器
        ab.connect(wamp_uri, on_wamp_success, on_wamp_error);

        //设置页码指示器
        localPageIndexUpdate(globalPageIndex);
    }

    function on_wamp_success(session) {
        global_session = session;
        console.log("wamp connected.");
    }

    function on_wamp_error(code, desc) {
        console.log("code:" + code + " desc:" + desc);
    }

    /**
     * 上一页按钮事件
     */
    btn_prePage.click(function (e) {
        e.preventDefault();
        var tempPageIndex = globalPageIndex - 1;
        if (pageIdInRange(tempPageIndex)) {
            globalPageIndex = tempPageIndex;
            remotePageUpdate();
            localPageIndexUpdate();

        } else {
            //out of range
        }
    });

    /**
     * 下一页按钮事件
     */
    btn_nextPage.click(function (e) {
        e.preventDefault();
        var tempPageIndex = globalPageIndex + 1;
        if (pageIdInRange(tempPageIndex)) {
            globalPageIndex = tempPageIndex;
            remotePageUpdate();
            localPageIndexUpdate();
        } else {
            //out of range
        }
    });

    /**
     * 检查页码是否超出范围
     * @param pageId
     * @returns {boolean}
     */
    function pageIdInRange(pageId) {
        return (pageId >= 1 && pageId <= pageCount) ? true : false;
    }

    /**
     * 设置页码的一系列操作
     * @param index
     */
    function localPageIndexUpdate() {

        $('#pageImgPool img').addClass('hidden');
        $('#pageImgPool #page' + globalPageIndex).removeClass('hidden');

        knobUpdate();
    }

    /**
     * 设置服务器页码
     * @param currentPageIndex
     */
    function remotePageUpdate() {
        if (global_session == null) {
            ab.connect(wamp_uri, on_wamp_success, on_wamp_error);  //重新连接wamp服务器

        }
        global_session.call("page#set", uemail, token, meetingId, globalPageIndex);
    }

    /**
     * 初始化页码指示器
     */
    function initPageKnob() {
        pageKnob.knob({
            min: 0,
            max: pageCount,
            readOnly: true
        });
    }

    /**
     * 触发页码指示器
     * @param index
     */
    function knobUpdate() {
        pageKnob.val(globalPageIndex).trigger('change');
    }

});
