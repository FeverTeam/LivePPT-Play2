package controllers;

import java.util.LinkedList;
import java.util.List;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.index;
import views.html.login;
import views.html.myppt;
import views.html.signup;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;

public class Frontend extends Controller {

	public static Result index() {
		return ok(index.render(null));
	}

	@With(CheckLoginAction.class)
	public static Result login() {
		session("name", "lbw");
		return ok(login.render(null));
	}
	
	@With(CheckLoginAction.class)
	public static Result signup(){
		return ok(signup.render(null));
	}
	
	
	@With(CheckLoginAction.class)
	public static Result myppt(){
		String displayname = session("displayname");
		Long userId = User.genUserIdFromSession(ctx().session());
		List<Ppt> ppts = User.find.byId(userId).ppts;
		return ok(myppt.render(null, displayname, ppts));
	}
	
	@With(CheckLoginAction.class)
	public static Result mymeeting(){
		Long userId = User.genUserIdFromSession(ctx().session());
		User user = User.find.byId(userId);
		
		List<Meeting> myFoundedMeetingList = user.myFoundedMeeting;
		List<Meeting> myAttendingMeetingList = new LinkedList<Meeting>();
		List<Attender> attendents = user.attendents;
		for (Attender attendding : attendents){
			myAttendingMeetingList.add(attendding.meeting);
		}
		return ok(views.html.mymeeting.render(null, myFoundedMeetingList, myAttendingMeetingList));
	}
	
	public static Result pptListForMeeting(){
		Long userId = User.genUserIdFromSession(ctx().session());
		List<Ppt> ppts = User.find.byId(userId).ppts;
		List<Ppt> convertedPpts = new LinkedList<Ppt>();
		for (Ppt ppt : ppts){
			if (ppt.isConverted){
			convertedPpts.add(ppt);
			}
		}
		return ok(views.html.pptListForMeeting.render(convertedPpts));
	}
	
	public static Result foundNewMeeting(Long pptId){
		Ppt ppt = Ppt.find.where().eq("id", pptId).findUnique();
		return ok(views.html.foundNewMeeting.render(ppt));
	}
	
	
	public static Result pptplainshow(Long pptid){
		int pageCount = Ppt.find.where().eq("id", pptid).findUnique().pagecount;
		return ok(views.html.pptplainshow.render(null, pptid, pageCount));
	}
	
	
	public static Result controlMeeting(Long meetingId){
		Meeting meeting = Meeting.find.byId(meetingId);
		Ppt ppt = meeting.ppt;
		return ok(views.html.controlMeeting.render(null, meeting, ppt));
	}
	
	public static Result joinMeeting(){
		return ok(views.html.joinMeeting.render());
	}
	
	public static Result viewMeeting(Long meetingId){
		Meeting meeting = Meeting.find.byId(meetingId);
		Ppt ppt = meeting.ppt;
		return ok(views.html.viewMeeting.render(null, meeting, ppt));
	}
	
}
