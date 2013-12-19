define(function (require, exports, module) {
    var pptImageSrc = function (pptId, pageId, uemail, token) {
        return "/ppt/pageImage?pptId=" + pptId + "&page=" + pageId + "&uemail=" + uemail + "&token=" + token;
    }

    module.exports = {
        pptImageSrc: pptImageSrc
    };
});