package controllers;

import java.util.LinkedList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.aboutUs;
import views.html.appDownload;
import views.html.controlMeeting;
import views.html.foundNewMeeting;
import views.html.index;
import views.html.joinMeeting;
import views.html.login;
import views.html.mymeeting;
import views.html.myppt;
import views.html.pptListForMeeting;
import views.html.pptplainshow;
import views.html.signup;
import views.html.viewMeeting;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;

/**
 * 页面Action
 * @author 梁博文
 *
 */
public class Frontend extends Controller {

	public static Result index() {
		return ok(index.render());
	}

	@With(CheckLoginAction.class)
	public static Result login() {
		return ok(login.render());
	}
	
	@With(CheckLoginAction.class)
	public static Result signup(){
		return ok(signup.render());
	}
	
	
	@With(CheckLoginAction.class)
	public static Result myppt(){
		User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
		List<Ppt> ppts = user.ppts;
		return ok(myppt.render(user, ppts));
	}
	
	@With(CheckLoginAction.class)
	public static Result mymeeting(){
		User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);		
		List<Meeting> myFoundedMeetingList = user.myFoundedMeeting;
		List<Meeting> myAttendingMeetingList = new LinkedList<Meeting>();
		List<Attender> attendents = user.attendents;
		for (Attender attendding : attendents){
			myAttendingMeetingList.add(attendding.meeting);
		}
		return ok(mymeeting.render(user, myFoundedMeetingList, myAttendingMeetingList));
	}
	
	@With(CheckLoginAction.class)
	public static Result pptListForMeeting(){
		User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
		List<Ppt> ppts = user.ppts;
		for (Ppt ppt : ppts){
			if (!ppt.isConverted){
				ppts.remove(ppt);
			}			
		}
		return ok(pptListForMeeting.render(ppts));
	}
	
	public static Result foundNewMeeting(Long pptId){
		Ppt ppt = Ppt.find.byId(pptId);
		return ok(foundNewMeeting.render(ppt));
	}
	
	@With(CheckLoginAction.class)
	public static Result pptplainshow(Long pptid){
		Ppt ppt = Ppt.find.byId(pptid);
		return ok(pptplainshow.render(ppt));
	}
	
	@With(CheckLoginAction.class)
	public static Result controlMeeting(Long meetingId){
		Meeting meeting = Meeting.find.byId(meetingId);
		return ok(controlMeeting.render(meeting));
	}
	
	public static Result joinMeeting(){
		return ok(joinMeeting.render());
	}
	
	@With(CheckLoginAction.class)
	public static Result viewMeeting(Long meetingId){
		Meeting meeting = Meeting.find.byId(meetingId);
		return ok(viewMeeting.render(meeting));
	}	
	
	public static Result appDownload(){
		return ok(appDownload.render());
	}
	
	public static Result aboutUs(){
		return ok(aboutUs.render());
	}
}