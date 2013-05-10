define(function(require, exports, module) {

	console.log("myppt");

	// require('uploadify');
    require('uploadifive');

  // $(function() {
  //   $('#file_upload').uploadify({
  //       'successTimeout' : 9999,
  //       'swf'      : '/assets/swf/uploadify.swf',
  //       'uploader' : '/pptupload',
  //       // Put your options here
  //       'fileTypeDesc' : 'PPT/PPTX文件',
  //       'fileTypeExts' : '*.ppt; *.pptx',
  //       'onUploadSuccess' : function(file, data, response) {
  //           location=location
  //       }
  //   });
  // });

    $(function(){
        $('#file_upload').uploadifive({
        'uploadScript' : '/pptupload',
        // Put your options here
        'onUploadComplete': function(file, data) {
            alert("上传成功！");
            location = location;
        },
        'onError': function(errorType) {
            alert('上传失败。[ ' + errorType+']');
        }
    });

    });

});