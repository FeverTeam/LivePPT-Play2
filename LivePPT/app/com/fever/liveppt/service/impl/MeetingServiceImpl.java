package com.fever.liveppt.service.impl;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;

import play.Logger;
import play.cache.Cache;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.JsonResult;

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

	@Override
	public ArrayNode getMyAttendingMeetings(Long userId) {
		// TODO Auto-generated method stub
		ArrayNode resultJson = new ArrayNode(JsonNodeFactory.instance);
		User user = User.find.byId(userId);
		if (user != null) {
			for (Attender attender : user.attendents) {
				resultJson.add(attender.meeting.toJson());
			}
		}
		return resultJson;
	}

	@Override
	public ArrayNode getMyFoundedMeetings(Long userId) {
		// TODO Auto-generated method stub
		ArrayNode resultJson = new ArrayNode(JsonNodeFactory.instance);
		User user = User.find.byId(userId);
		if (user != null) {
			for (Meeting meeting : user.myFoundedMeeting) {
				resultJson.add(meeting.toJson());
			}
		}
		return resultJson;
	}

	@Override
	public JsonResult getMeetingInfo(Long meetingId) {
		// TODO Auto-generated method stub
		JsonResult resultJson;
		Meeting meeting = Meeting.find.byId(meetingId);
		if (meeting==null){
			resultJson = new JsonResult(false, null, "没有该会议。");
		} else {
			resultJson = new JsonResult(true, null, meeting.toJson());
		}
		return resultJson;
	}

	@Override
	public JsonResult setMeetingPageIndex(Long meetingId, Long pageIndex) {
		// TODO Auto-generated method stub		
		String cacheKey=meetingId.toString();
		Logger.info(meetingId + "-" + pageIndex);
		Cache.set(cacheKey, pageIndex);
		JsonResult resultJson = new JsonResult(true);		
		return resultJson;
	}

}
