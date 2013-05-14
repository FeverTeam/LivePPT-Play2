define(function(require, exports, module) {
	console.log("mymeeting");

	$('li#mymeeting').addClass('active');

	var
	modalPptList = $('#modal-ppt-list'),
	modalPptList_body = $('#modal-ppt-list .modal-body'),
	btnFoundNewMeeting = $('#btn-found-new-meeting'),
	divChoosePpt = $('#div-choose-ppt'),
	divSetupMeeting = $('#div-setup-meeting');

	// 发起会议

	//按钮事件 - 删除会议
	$('.delete-meeting').on('click', function(e){
		var meetingId = $(this).attr('meetingid');
		$.ajax({
			type: 'POST',
			url: '/deleteMeeting',
			data: {
				meetingid: meetingId
			},
			success: function(data, textStatus, jqXHR){
				if (textStatus=='success'){
					window.location = window.location;
				}
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
		modalPptList
		.removeData('modal')
		.modal({
			remote: '/pptListForMeeting'
		});
	});


	//按钮事件 - 选择指定PPT进入填写会议详情页面
	modalPptList.on('click', '.btn-choose-ppt-new-meeting', function(e){
		var pptid = $(this).attr('pptid');
		modalPptList_body
		.load('/foundNewMeeting/'+pptid, function(responseText, textStatus, XMLHttpRequest){});
	});

	//按钮事件 - 启动会议
	modalPptList.on('click', '#launchMeeting', function(e){
		e.preventDefault();
		$.ajax({
			type: 'POST',
			url: '/foundNewMeeting',
			data: {
				topic: $('#inputTopic').val(),
				pptid: $(this).attr('pptid')
			},
			success: function(data, textStatus, jqXHR){
				if (textStatus=='success'){
					window.location = window.location;
				}
			}
		});
	});


	// 加入会议
	var modalJoinMeeting = $('#modal-ppt-list');
	var btnQuitMeeting = $('.quit-meeting');
	var btnViewMeeting = $('.view-meeting');

	//按钮事件 - 加入会议
	$('#btn-showpage-join-meeting').on('click', function(e){
		modalJoinMeeting
		.removeData('modal')
		.modal({
			remote: '/joinMeeting'
		});
	});

	modalJoinMeeting.on('click', '#btn-join-meeting', function(e){
		e.preventDefault();
		$.ajax({
			type: "POST",
			url: "/joinMeeting",
			data: {
				inputMeetingId: $('#inputMeetingId').val()
			},
			success: function(dataJson, textStatus, jqXHR){
				if (dataJson.success){
					window.location = window.location;
				} else {
					alert(dataJson.message);
				}
			}
		})
	});


	btnViewMeeting.on('click', function(e){
		var meetingId = $(this).attr('meetingid');
		window.location = '/viewMeeting/'+meetingId;
	});

	btnQuitMeeting.on('click', function(e){		
		alert("btnQuitMeeting");
	});
});