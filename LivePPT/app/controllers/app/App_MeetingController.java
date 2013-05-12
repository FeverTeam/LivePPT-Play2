package controllers.app;

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

	public static WebSocket<String> testWS() {
		return new WebSocket<String>() {

			// Called when the Websocket Handshake is done.
			public void onReady(WebSocket.In<String> in,
					final WebSocket.Out<String> out) {

				// For each event received on the socket,
				in.onMessage(new Callback<String>() {
					public void invoke(String event) {

						// Log events to the console
						Logger.info(event);
						out.write("i am server");

					}
				});

				// When the socket is closed.
				in.onClose(new Callback0() {
					public void invoke() {

						Logger.info("Disconnected");

					}
				});

				// Send a single 'Hello!' message
				out.write("Hello!");

			}

		};
	}

}
