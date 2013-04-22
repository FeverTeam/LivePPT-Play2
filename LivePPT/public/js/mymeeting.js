define(function(require, exports, module) {
	console.log("mymeeting");

	var
	modalPptList = $('#modal-ppt-list'),
	modalPptList_body = $('#modal-ppt-list .modal-body');
	btnFoundNewMeeting = $('#btn-found-new-meeting'),
	divChoosePpt = $('#div-choose-ppt'),
	divSetupMeeting = $('#div-setup-meeting');

	// $('body').on('hidden', '#modal-ppt-list', function () {
	// 	$(this).removeData('modal');
	// })

	btnFoundNewMeeting.on('click', function(e){
		modalPptList
		.removeData('modal')
		.modal({
			remote: '/pptListForMeeting'
		});
	});

	modalPptList.on('click', '.btn-choose-ppt-new-meeting', function(e){
		var pptid = $(this).attr('pptid');
		modalPptList_body
		.load('/foundNewMeeting/'+pptid, function(responseText, textStatus, XMLHttpRequest){
			console.log(responseText);
			console.log(textStatus);
			console.log(XMLHttpRequest);
		});

	});

});