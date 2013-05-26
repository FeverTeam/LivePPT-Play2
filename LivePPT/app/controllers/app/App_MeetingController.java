package controllers.app;

import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;
import com.google.inject.Inject;

public class App_MeetingController extends Controller {
	@Inject
	MeetingService meetingService;

	/**
	 * 获取用户所有观看的会议的列表
	 * @param userId 用户Id
	 * @return
	 */
	public Result getMyAttendingMeetings() {
		Map<String, String[]> params = request().queryString();
		
		//检查必须的参数是否存在
		Set<String> keySet = params.keySet();
		if (!keySet.contains("userId")){
			return ok(new JsonResult(false, null, "userId字段不存在"));
		}
		
		//获取参数
		Long userId = Long.parseLong(params.get("userId")[0]);
		
		ArrayNode attendingMeetingsArrayNode = meetingService
				.getMyAttendingMeetings(userId);
		ObjectNode resultJson = new JsonResult(true, attendingMeetingsArrayNode);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}
	
	/**
	 * 获取用户所有自己发起的会议的列表
	 * @return
	 */
	public Result getMyFoundedMeetings(){
		Map<String, String[]> params = request().queryString();
		
		//检查必须的参数是否存在
		Set<String> keySet = params.keySet();
		if (!keySet.contains("userId")) {
			return ok(new JsonResult(false, null, "userId字段不存在"));
		}
		
		//获取参数
		Long userId = Long.parseLong(params.get("userId")[0]);
		
		ArrayNode foundedMeetingsArrayNode = meetingService
				.getMyFoundedMeetings(userId);
		ObjectNode resultJson = new JsonResult(true, foundedMeetingsArrayNode);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}
	

	/**
	 * 获取指定会议的信息
	 * @param meetingId
	 * @return
	 */
	public Result getMeetingInfo(Long meetingId){
		JsonResult resultJson = meetingService.getMeetingInfo(meetingId);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}
	
	/**
	 * 设置会议的直播PPT页码
	 * @param meetingId
	 * @param pageIndex
	 * @return
	 */
	public Result setMeetingPageIndex() {
		Long meetingId;
		Long pageIndex;
		// 获取POST参数
		Map<String, String[]> params = request().body().asFormUrlEncoded();

		Set<String> keySet; 

		// 获取参数并检查必须的参数是否存在
		try
		{
			keySet = params.keySet();
			meetingId = Long.valueOf(params.get("meetingId")[0]);
		}catch (Exception e)
		{
			System.out.println(e);
			return ok(new JsonResult(false, StatusCode.MEETING_ID_ERROR, "MeetingId字段错误."));
		}
		
		try
		{
			pageIndex = Long.valueOf(params.get("pageIndex")[0]);
		}catch (Exception e)
		{
			System.out.println(e);
			return ok(new JsonResult(false, StatusCode.MEETING_PAGEINDEX_ERROR, "pageIndex字段错误."));
		}
		
		JsonResult resultJson = meetingService.setMeetingPageIndex(meetingId, pageIndex);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}

}
