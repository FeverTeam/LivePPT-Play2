define(function (require, exports, module) {
//    var local_debug = true;
    var local_debug = false;

    //wamp_uri
    var _wamp_port = "9000";
    var _wamp_sub_uri = "/wamp";
    var _wamp_ip = (local_debug) ? "localhost" : "cloudslides.net";
    var wamp_uri = "ws://" + _wamp_ip + ":" + _wamp_port + _wamp_sub_uri; //组装wamp_uri

    //预加载的图片数量
    var preload_img_count = 2;

    //exports
    exports.preload_img_count = preload_img_count;
    exports.wamp_uri = wamp_uri;
});