package controllers.app;

import java.util.Map;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.JsonResult;
import com.google.inject.Inject;

public class App_MeetingController extends Controller {
	@Inject
	MeetingService meetingService;

	/**
	 * 获取用户所有观看的会议的列表
	 * @return
	 */
	public Result getMyAttendingMeetings() {
		ObjectNode resultJson;
		Long userId = Long.parseLong(request().getQueryString("userId"));
//		Long userId = Long.parseLong(request().body().asFormUrlEncoded().get("userId")[0]);
		ArrayNode attendingMeetingsArrayNode = meetingService
				.getMyAttendingMeetings(userId);
		resultJson = JsonResult.genResultJson(true, attendingMeetingsArrayNode);
		Logger.info(Json.stringify(resultJson));
		return ok(resultJson);
	}
	
	/**
	 * 获取用户所有自己发起的会议的列表
	 * @return
	 */
	public Result getMyFoundedMeetings(){
		Long userId = Long.parseLong(request().getQueryString("userId"));
		ArrayNode foundedMeetingsArrayNode = meetingService
				.getMyFoundedMeetings(userId);
		ObjectNode resultJson = JsonResult.genResultJson(true, foundedMeetingsArrayNode);
		Logger.info(Json.stringify(resultJson));
		return ok(resultJson);
	}

}
