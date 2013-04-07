define(function(require, exports, module) {

	console.log("myppt");

	require('fineuploader');

    $(document).ready(function() {
    $('#restricted-fine-uploader').fineUploader({
      request: {
        endpoint: '/pptupload'
      },
      multiple: false,
      text: {
        uploadButton: '快速上传新的PPT/PPTX文件'
      },
      showMessage: function(message) {
        // Using Bootstrap's classes
        $('#restricted-fine-uploader').append('<div class="alert alert-error">' + message + '</div>');
      }
    }).on('complete', function(event, id, fileName, responseJSON) {
      
    });
  });

});