define(function(require, exports, module) {

	console.log("myppt");

	require('uploadify');

  $(function() {
    $('#file_upload').uploadify({
        'successTimeout' : 9999,
        'swf'      : '/assets/swf/uploadify.swf',
        'uploader' : '/pptupload',
        // Put your options here
        'fileTypeDesc' : 'PPT/PPTX文件',
        'fileTypeExts' : '*.ppt; *.pptx',
        'onUploadSuccess' : function(file, data, response) {
            alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
        }
    });
  });

});