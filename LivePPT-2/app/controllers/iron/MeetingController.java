package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.MeetingService;

import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.meeting.MeetingException;
import com.liveppt.utils.exception.meeting.MeetingIdErrorException;
import com.liveppt.utils.exception.meeting.MeetingPageIndexErrorException;
import com.liveppt.utils.exception.meeting.MeetingTopicErrorException;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.exception.user.UserNoLoginException;
import com.liveppt.utils.models.MeetingJson;
import com.liveppt.utils.models.MeetingReader;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.mvc.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 会议接口
 * Author 黎伟杰
 */

public class MeetingController extends Controller {
    @Inject
    MeetingService meetingService;

    //KEY FIELD

    public static String KEY_USER_ID = "userId";
    public static String KEY_PPT_ID = "pptId";
    public static String KEY_MEETING_TOPIC = "meetingTopic";
    public static String KEY_MEETING_ID = "meetingId";
    public static String KEY_MEETING_CURRENT_PAGE = "meetingCurrentPage";

    //tool

    private Map<String,String> meetingReaderToMap(MeetingReader meetingReader){
        Map<String,String> keyValue = new HashMap<>();
        keyValue.put(KEY_MEETING_ID, String.valueOf(meetingReader.getMeetingId()));
        keyValue.put(KEY_USER_ID, String.valueOf(meetingReader.getUserId()));
        keyValue.put(KEY_PPT_ID, String.valueOf(meetingReader.getPptId()));
        keyValue.put(KEY_MEETING_TOPIC, meetingReader.getTopic());
        keyValue.put(KEY_MEETING_CURRENT_PAGE, String.valueOf(meetingReader.getCurrentPageIndex()));
        return  keyValue;
    }

    //API

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

    public Result joinMeeting() {
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

            meetingReader = meetingService.joinMeeting(meetingReader);

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

            meetingReader = meetingService.quitMeeting(meetingReader);

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

    public Result getMyFoundedMeetings() {
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_USER_ID);
            if (s_id==null) throw new UserNoLoginException();
            Long id = Long.valueOf(s_id);
            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(id);

            //提取信息
            List<MeetingReader> meetingReaders = meetingService.getMyFoundedMeetings(meetingReader);

            //将Set<PptReader>转换为Json格式
            ArrayNode meetingJsons = new ArrayNode(JsonNodeFactory.instance);
            for (MeetingReader meetingReader0:meetingReaders){
                MeetingJson meetingJson = new MeetingJson();
                meetingJson.setStringField(meetingReaderToMap(meetingReader0));
                meetingJsons.add(meetingJson);
            }

            resultJson = new ResultJson(meetingJsons);
        } catch (UserException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);
    }

    public Result getMyAttendingMeetings() {
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_USER_ID);
            if (s_id==null) throw new UserNoLoginException();
            Long id = Long.valueOf(s_id);
            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(id);

            //提取信息
            List<MeetingReader> meetingReaders = meetingService.getMyAttendingMeetings(meetingReader);

            //将Set<PptReader>转换为Json格式
            ArrayNode meetingJsons = new ArrayNode(JsonNodeFactory.instance);
            for (MeetingReader meetingReader0:meetingReaders){
                MeetingJson meetingJson = new MeetingJson();
                meetingJson.setStringField(meetingReaderToMap(meetingReader0));
                meetingJsons.add(meetingJson);
            }

            resultJson = new ResultJson(meetingJsons);
        } catch (UserException e) {
            e.printStackTrace();
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);
    }

    public Result setMeetingPage() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        ResultJson resultJson = null;

        try {
            //从Session获取用户登录信息
            String s_userId = ctx().session().get(KEY_USER_ID);
            //从request里面获取参数
            String s_meetingId = params.get(KEY_MEETING_ID)[0];
            String s_meetingPage = params.get(KEY_MEETING_CURRENT_PAGE)[0];
            //检查参数
            if (s_userId==null)  throw new UserNoLoginException();
            if (s_meetingId==null) throw new MeetingIdErrorException();
            if (s_meetingPage==null) throw new MeetingPageIndexErrorException();
            //转换参数
            Long userId = Long.parseLong(s_userId);
            Long meetingId = Long.parseLong(s_meetingId);
            Long meetingPage = Long.parseLong(s_meetingPage);

            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(userId).setMeetingId(meetingId).setCurrentPageIndex(meetingPage);

            meetingReader = meetingService.setMeetingPageIndex(meetingReader);

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

    public Result getMeetingInfoAsFounder() {

        ResultJson resultJson = null;

        try {
            //从Session获取用户登录信息
            String s_userId = ctx().session().get(KEY_USER_ID);
            //从request里面获取参数
            String s_meetingId = request().getQueryString(KEY_MEETING_ID);
            //检查参数
            if (s_userId==null)  throw new UserNoLoginException();
            if (s_meetingId==null) throw new MeetingIdErrorException();
            //转换参数
            Long userId = Long.parseLong(s_userId);
            Long meetingId = Long.parseLong(s_meetingId);

            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(userId).setMeetingId(meetingId);

            meetingReader = meetingService.getMeetingInfoAsFounder(meetingReader);

            MeetingJson meetingJson = new MeetingJson();
            meetingJson.setStringField(meetingReaderToMap(meetingReader));

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

    public Result getMeetingInfoAsAttender() {

        ResultJson resultJson = null;

        try {
            //从Session获取用户登录信息
            String s_userId = ctx().session().get(KEY_USER_ID);
            //从request里面获取参数
            String s_meetingId = request().getQueryString(KEY_MEETING_ID);
            //检查参数
            if (s_userId==null)  throw new UserNoLoginException();
            if (s_meetingId==null) throw new MeetingIdErrorException();
            //转换参数
            Long userId = Long.parseLong(s_userId);
            Long meetingId = Long.parseLong(s_meetingId);

            MeetingReader meetingReader = new MeetingReader();
            meetingReader.setUserId(userId).setMeetingId(meetingId);

            meetingReader = meetingService.getMeetingInfoAsAttender(meetingReader);

            MeetingJson meetingJson = new MeetingJson();
            meetingJson.setStringField(meetingReaderToMap(meetingReader));

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

    public static WebSocket<String> viewWebsocket() {

        return null;
    }

}
