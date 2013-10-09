define(function(require, exports, module) {
	console.log("mymeeting");
	$('li#mymeeting').addClass('active');

	$('#myTab a').click(function (e) {
	  	e.preventDefault();
	 	$(this).tab('show');
	})

	require('cookies');

	var
	modalPptList = $('#modal-ppt-list'),
	modalPptList_body = $('#modal-ppt-list .modal-dialog .modal-content .modal-body'),
	btnFoundNewMeeting = $('#btn-found-new-meeting'),
	divChoosePpt = $('#div-choose-ppt'),
	divSetupMeeting = $('#div-setup-meeting');

	// 发起会议

	//按钮事件 - 删除会议
	$('.delete-meeting').on('click', function(e){
		var meetingId = $(this).attr('meetingid');
		$.ajax({
			type: 'POST',
			url: '/meeting/delete',
			data: {
				meetingId: meetingId
			},
			success: function(res, status){
				console.log(res.message);
				if (!res.retcode){
					window.location = window.location;
				}
				else {
					showmsg('删除失败');
				};				
			},
			headers: {
				'uemail': $.cookie('uemail'),
				'token': $.cookie('token')
			}
		});
	});

	//按钮事件 - 进入控制会议
	$('.enter-control-meeting').on('click', function(e){
		var meetingId = $(this).attr('meetingid');
		window.location = '/controlMeeting/'+meetingId;
	});

	//按钮事件 - 发起会议
	btnFoundNewMeeting.on('click', function(e){
	    modalPptList_body.html("正在加载 Loading...");
		modalPptList
		.load( '/pptListForMeeting',function(responseText, textStatus, XMLHttpRequest){})
		.modal('show');
	});


	//按钮事件 - 选择指定PPT进入填写会议详情页面
	modalPptList.on('click', '.btn-choose-ppt-new-meeting', function(e){
	    modalPptList_body.html("正在加载 Loading...");

		var pptid = $(this).attr('pptid');
		//modalPptList.modal('hide').removeData('modal');

		modalPptList
		.load('/foundNewMeeting/'+pptid, function(responseText, textStatus, XMLHttpRequest){})
		.modal('show');
	});

	//按钮事件 - 启动会议
	modalPptList.on('click', '#launchMeeting', function(e){
		e.preventDefault();

		$.ajax({
			type: 'POST',
			url: '/meeting/create',
			data: {
				topic: $('#inputTopic').val(),
				pptId: $(this).attr('pptid')
			},
			success: function(res, status){
				if (!res.status){
					window.location = window.location;
				}
				else {
					showmsg('创建失败');
				};
			},
			headers: {
				'uemail': $.cookie('uemail'),
				'token': $.cookie('token')
			}
		});
	});


	// 加入会议
	var modalJoinMeeting = $('#modal-ppt-list');
	var btnQuitMeeting = $('.quit-meeting');
	var btnViewMeeting = $('.view-meeting');

	//按钮事件 - 加入会议
	$('#btn-showpage-join-meeting').on('click', function(e){
        modalPptList_body.html("正在加载 Loading...");
		modalJoinMeeting
		.load('/joinMeeting')
		.modal('show');
	});

	modalJoinMeeting.on('click', '#btn-join-meeting', function(e){
		e.preventDefault();
		$.ajax({
			type: "POST",
			url: "/meeting/join",
			data: {
				meetingId: $('#inputMeetingId').val()
			},
			success: function(res, status){
				console.log(res.message);
				if (!res.retcode){
					window.location = window.location;
				} else {
					showmsg(res.message);
				}
			},
			headers: {
				'uemail': $.cookie('uemail'),
				'token': $.cookie('token')
			}
		})
	});


	btnViewMeeting.on('click', function(e){
		var meetingId = $(this).attr('meetingid');
		window.location = '/viewMeeting/'+meetingId;
	});

	btnQuitMeeting.on('click', function(e){	
		var meetingId = $(this).attr('meetingid');
		var userId = $(this).attr('userId');
		$.ajax({
			type: 'POST',
			url: '/meeting/quit',
			data: {
				meetingId: meetingId,
			},
			success: function(res, status){
				console.log(res.message);
				if (!res.retcode){
					window.location = window.location;
				} else {
					showmsg(res.message);
				}
			},
			headers: {
				'uemail': $.cookie('uemail'),
				'token': $.cookie('token')
			}
		});	
	});
});