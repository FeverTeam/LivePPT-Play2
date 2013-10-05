define(function(require, exports, module) {

	console.log("myppt");

  $('li#myppt').addClass('active');

    require('uploadifive');

    $(function(){
        $('#file_upload').uploadifive({
        'uploadScript' : '/ppt/upload',
        'uploadLimit'  : 1,
        'fileType'     : ['application/vnd.ms-powerpoint','application/vnd.openxmlformats-officedocument.presentationml.presentation','application/wps-office.ppt'],
        'fileObjName'  : 'pptFile',
        //自定义Header
        'customHeaders':[{name:'uemail', value:uemail}, {name:'token', value:token}],


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