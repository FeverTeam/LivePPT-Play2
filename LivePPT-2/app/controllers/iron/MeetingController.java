package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.MeetingService;

import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.meeting.MeetingException;
import com.liveppt.utils.exception.meeting.MeetingIdErrorException;
import com.liveppt.utils.exception.meeting.MeetingTopicErrorException;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.exception.user.UserNoLoginException;
import com.liveppt.utils.models.MeetingJson;
import com.liveppt.utils.models.MeetingReader;
import play.mvc.*;

import java.util.Map;

/**
 * 会议接口
 * Author 黎伟杰
 */

public class MeetingController extends Controller {
    @Inject
    MeetingService meetingService;

    public static String KEY_USER_ID = "userId";
    public static String KEY_PPT_ID = "pptId";
    public static String KEY_MEETING_TOPIC = "meetingTopic";
    public static String KEY_MEETING_ID = "meetingId";

    public Result foundNewMeeting() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;

        try {
            //从Session获取用户登录信息
            String s_userId = ctx().session().get(KEY_USER_ID);
            //从request里面获取参数
            String s_pptId = params.get(KEY_PPT_ID)[0];
            String topic = params.get(KEY_MEETING_TOPIC)[0];
            //检查参数
            if (s_userId==null)  throw new UserNoLoginException();
            if (s_pptId==null)  throw new MeetingIdErrorException();
            if (topic==null) throw new MeetingTopicErrorException();
            //转换参数
            Long pptId = Long.parseLong(s_pptId);
            Long userId = Long.parseLong(s_userId);

            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setPptId(pptId).setUserId(userId).setTopic(topic);

            meetingReader = meetingService.foundNewMeeting(meetingReader);

            MeetingJson meetingJson = new MeetingJson();

            resultJson = new ResultJson(meetingJson);
        }  catch (MeetingException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }  catch (UserException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);

    }

    public Result deleteMeeting() {

        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;

        try {
            //从Session获取用户登录信息
            String s_userId = ctx().session().get(KEY_USER_ID);
            //从request里面获取参数
            String s_meetingId = params.get(KEY_MEETING_ID)[0];
            //检查参数
            if (s_userId==null)  throw new UserNoLoginException();
            if (s_meetingId==null) throw new MeetingIdErrorException();
            //转换参数
            Long userId = Long.parseLong(s_userId);
            Long meetingId = Long.parseLong(s_meetingId);

            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(userId).setMeetingId(meetingId);

            meetingReader = meetingService.deleteMeeting(meetingReader);

            MeetingJson meetingJson = new MeetingJson();

            resultJson = new ResultJson(meetingJson);
        }  catch (MeetingException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }  catch (UserException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);
    }

    public Result quitMeeting() {
        return TODO;
    }

    public Result setMeetingPage() {
        return TODO;
    }

    public static Result joinMeeting() {
        return TODO;
    }

    public static WebSocket<String> viewWebsocket() {

        return null;
    }

}
