define(function(require, exports, module) {

	console.log("myppt");

  $('li#myppt').addClass('active');


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
        'uploadScript' : '/ppt/upload',
        'uploadLimit'  : 1,
        'fileType'     : ['application/vnd.ms-powerpoint','application/vnd.openxmlformats-officedocument.presentationml.presentation','application/wps-office.ppt'],
        'buttonText'   : '上传PPT/PPTX',
        'buttonClass'  : 'btn btn-success',
        // Put your options here
        'onUploadComplete': function(file, data) {
            alert("上传成功！");
            //location = location;
        },
        'onError': function(errorType) {
          $('#file_upload').uploadifive('clearQueue');
          switch (errorType) {
            case "FORBIDDEN_FILE_TYPE":
              alertMessage = "请选择PPT/PPTX文件~";
              break;
            case "QUEUE_LIMIT_EXCEEDED":
              alertMessage = "每次只可上传一个文件哦~";
              break;
          }
          if (alertMessage){
            alert(alertMessage);
          }
          
        }
    });

    });

});