package controllers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.cache.Cache;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.google.inject.Inject;

/**
 * 有关Meeting的数据接口
 * @author 梁博文
 *
 */
public class MeetingController extends Controller {

	@Inject
	MeetingService meetingService;

	public Result foundNewMeeting() {
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		String topic = data.get("topic")[0];
		Long pptId = Long.parseLong(data.get("pptid")[0]);
		Long userId = User.genUserIdFromSession(ctx().session());
		meetingService.foundNewMeeting(userId, pptId, topic);
		return ok("foundNewMeeting");
	}

	public Result deleteMeeting() {
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		Long meetingId = Long.parseLong(data.get("meetingid")[0]);
		meetingService.deleteMeeting(meetingId);
		return ok("deleteMeeting" + meetingId);
	}

	public Result setMeetingPage() {
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		Long meetingId = Long.parseLong(data.get("meetingId")[0]);
		Long currentPageIndex = Long.parseLong(data.get("currentPageIndex")[0]);
		String cacheKey=meetingId.toString();
		Logger.info(meetingId + "-" + currentPageIndex);
		Cache.set(cacheKey, currentPageIndex);
		return ok("setMeetingPage");
	}

	public static Result joinMeeting() {
		Map<String, String[]> data = request().body().asFormUrlEncoded();
		Long meetingId = Long.parseLong(data.get("inputMeetingId")[0]);
		Long userId = User.genUserIdFromSession(ctx().session());

		Meeting meeting = Meeting.find.byId(meetingId);
		ObjectNode result = Json.newObject();
		if (meeting == null) {
			result.put("success", false);
			result.put("message", "没有这个会议");
			return ok(result);
		}

		User user = User.find.byId(userId);
		if (user == null) {
			result.put("success", false);
			result.put("message", "没有这个用户。");
			return ok(result);
		}
		
		List<Attender> attendings = user.attendents;
		boolean isAttended = false;
		for (Attender attending : attendings){
			if (attending.meeting.id.equals(meeting.id)){
				isAttended = true;
				break;
			}
		}
		
		if (!isAttended){
			Attender newAttending = new Attender(meeting, user);
			newAttending.save();
		}

		result.put("success", true);
		return ok(result);
	}

	public static WebSocket<String> viewWebsocket() {
		return new WebSocket<String>() {
			
			@Override
			public void onReady(WebSocket.In<String> in, final WebSocket.Out<String> out) {
				// For each event received on the socket,
				in.onMessage(new Callback<String>() {
					@Override
					public void invoke(String meetingIdStr) {
						// Log events to the console
						Long meetingId = Long.parseLong(meetingIdStr);
						Logger.info("WebSocket Started by meetingId="+meetingIdStr);
						final Long pptId = Meeting.find.byId(meetingId).ppt.id;
						final String cacheKey=Long.toString(meetingId);   
   
						Akka.system().scheduler().schedule(
							Duration.Zero(),
							Duration.create(10, TimeUnit.MILLISECONDS), 
							new Runnable(){
								Long currentIndex;
								Long temp = (long)1;
								@Override
								public void run() {
									// TODO Auto-generated method stub	
									currentIndex = (Long) Cache.get(cacheKey);
									if (currentIndex==null){
										currentIndex=(long) 1;
									}
									if (!temp.equals(currentIndex)){
										temp = currentIndex;
										Logger.info(pptId+"-"+currentIndex);
										out.write(pptId+"-"+currentIndex);
									}		 		    	  
								}	        		   
							},
							Akka.system().dispatcher());	   
					} 
				});		      
			}
		  };
		}
}
