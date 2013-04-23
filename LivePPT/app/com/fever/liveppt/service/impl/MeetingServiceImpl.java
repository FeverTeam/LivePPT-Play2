package com.fever.liveppt.service.impl;

import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;

public class MeetingServiceImpl implements MeetingService {
	
	@Override
	public void deleteMeeting(Long meetingId) {
		// TODO Auto-generated method stub
		Meeting meeting = Meeting.find.byId(meetingId);
		meeting.delete();
	}

	@Override
	public void foundNewMeeting(Long founderId, Long pptId, String topic) {
		// TODO Auto-generated method stub
		User founder = User.find.byId(founderId);
		Ppt ppt = Ppt.find.byId(pptId);
		
		Meeting meeting = new Meeting();
		meeting.founder = founder;
		meeting.ppt = ppt;
		meeting.topic = topic;
		meeting.save();		
	}

}
