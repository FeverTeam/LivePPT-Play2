package controllers;

import java.util.Map;

import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.google.inject.Inject;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

public class MeetingController extends Controller {
	
	@Inject
	MeetingService meetingService;
	
	public Result foundNewMeeting(){
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		String topic = data.get("topic")[0];
		Long pptId = Long.parseLong(data.get("pptid")[0]);
		Long userId = User.genUserIdFromSession(ctx().session());
		meetingService.foundNewMeeting(userId, pptId, topic);
		return ok("foundNewMeeting");
	}
	
	public Result deleteMeeting(){
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		Long meetingId = Long.parseLong(data.get("meetingid")[0]);
		meetingService.deleteMeeting(meetingId);
		return ok("deleteMeeting"+meetingId);
	}
	
	public Result setMeetingPage(){
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		Long meetingId = Long.parseLong(data.get("meetingId")[0]);
		Long currentPageIndex = Long.parseLong(data.get("currentPageIndex")[0]);
		Logger.info(meetingId+"-"+currentPageIndex);
		return ok("setMeetingPage");
	}
}
