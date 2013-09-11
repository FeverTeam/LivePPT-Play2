package controllers.app;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.exception.meeting.AttendingExistedException;
import com.fever.liveppt.exception.meeting.MeetingNotExistedException;
import com.fever.liveppt.exception.meeting.MeetingPermissionDenyException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.*;
import com.google.inject.Inject;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class App_MeetingController extends Controller {
    @Inject
    MeetingService meetingService;

    /**
     * 发起新会议
     * @return
     */
    public Result createMeeting()
    {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "pptId"))
            {
                throw new InvalidParamsException();
            }

            if(!ControllerUtils.isFieldNotNull(params,"topic"))
            {
                throw new InvalidParamsException();
            }

            //获取参数

            Long pptId = Long.valueOf(params.get("pptId")[0]);
            if (pptId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }
            String topic = params.get("topic")[0];

            //创建新会议条目
            resultJson = meetingService.createMeeting(userEmail,pptId,topic);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

          } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 删除用户自己发起的会议
     * @return
     */
    public Result deleteMeeting()
    {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
           String userEmail = TokenAgent.validateTokenFromHeader(request());
            //  String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "meetingId"))
            {
                throw new InvalidParamsException();
            }


            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            //删除
             meetingService.deleteMeeting(userEmail,meetingId);
            resultJson = new ResultJson(StatusCode.SUCCESS,"success",null);
            //若返回JSON为空，设为位置错误
           // resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 修改用户自己发起的会议
     * @return
     */
    public Result updateMeeting() {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "meetingId"))
            {
                throw new InvalidParamsException();
            }

            if(!ControllerUtils.isFieldNotNull(params, "pptId"))
            {
                throw new InvalidParamsException();
            }

            if(!ControllerUtils.isFieldNotNull(params, "topic"))
            {
                throw new InvalidParamsException();
            }
            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            Long pptId = Long.valueOf(params.get("pptId")[0]);
            if(pptId == null) {
                //长整形转换失败
                throw new InvalidParamsException();
            }

            String topic = params.get("topic")[0];

            //修改会议
            resultJson = meetingService.updateMeeting(userEmail, meetingId, pptId, topic);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        }  catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }
          catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 加入观看指定会议
     * @return
     */
    public Result joinMeeting()
    {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "bowen@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "meetingId"))
            {
                throw new InvalidParamsException();
            }


            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            //加入会议
            resultJson = meetingService.joinMeeting(userEmail, meetingId);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
           resultJson = new ResultJson(e);
       }
          catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (AttendingExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 退出观看指定会议
     * @return
     */
    public Result quitMeeting()
    {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
             String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "bowen@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "meetingId"))
            {
                throw new InvalidParamsException();
            }


            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            //退出会议
            resultJson = meetingService.quitMeeting(userEmail, meetingId);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
        resultJson = new ResultJson(e);
         }
        catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 设置会议的PPT页码
     * @return
     */
    public Result setPage()
    {
        ResultJson resultJson = null;
        try{
            //验证Token并提取userEmail
             String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "bowen@gmail.com";
            //获取POST参数
            Map<String,String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if(!ControllerUtils.isFieldNotNull(params, "meetingId"))
            {
                throw new InvalidParamsException();
            }

            if(!ControllerUtils.isFieldNotNull(params, "pageIndex"))
            {
                throw new InvalidParamsException();
            }
            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            Long pageIndex = Long.valueOf(params.get("pageIndex")[0]);
            if (pageIndex == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }
            //设置会议页码
            resultJson = meetingService.setPage(userEmail, meetingId, pageIndex);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        }
        catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        }
        // resultJson = new ResultJson(e);
        // }

        return ok(resultJson);
    }
    /////////////////////////////////////////////////
    //GET
    /////////////////////////////////////////////////

    /**
     * 获取用户所有自己发起的会议
     * @return
     */
    public Result getMyFoundedMeetings(){
        ResultJson resultJson = null;
        try{
            String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "weijie@gmail.com";
            //获取ppt
            List<Meeting> meetingList = meetingService.getMyFoundedMeetings(userEmail);

            //组装MEETING信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Meeting meeting : meetingList) {
                pptInfoArraryNode.add(meeting.toJson());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        }
         catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }


    /**
     * 获取用户所有观看的会议
     * @return
     */
    public Result getMyAttendingMeeting()
    {
        ResultJson resultJson = null;
        try{
            String userEmail = TokenAgent.validateTokenFromHeader(request());
           // String userEmail = "bowen@gmail.com";
            //获取ppt
            List<Meeting> meetingList = meetingService.getMyAttendingMeetings(userEmail);

            //组装MEETING信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Meeting meeting : meetingList) {
                pptInfoArraryNode.add(meeting.toJson());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 获取指定会议信息
     * @return
     */
    public Result getMeetingInfo()
    {
        ResultJson resultJson = null;
        try{
            //获取GET参数
            Map<String, String[]> params = request().queryString();
            if (params == null) {
                throw new InvalidParamsException();
            }

            //检查字段参数
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
                throw new InvalidParamsException();
            }

            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

           resultJson = meetingService.getMeetingInfo(meetingId);


            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        }  catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }
    //**************************************************************************************************************
    /**
     * 获取用户所有观看的会议的列表
     *
     * @param
     * @return
     */
   /* public Result getMyAttendingMeetings() {
        Map<String, String[]> params = request().queryString();

        JsonResult resultJson;

        //检查userId
        resultJson = checkUserId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        //获取参数
        Long userId = Long.parseLong(params.get("userId")[0]);

        ArrayNode attendingMeetingsArrayNode = meetingService
                .getMyAttendingMeetings(userId);
        resultJson = new JsonResult(true, attendingMeetingsArrayNode);
        return ok(resultJson);
    }    */

    /**
     * 建立新的meeting
     *
     * @return
     */
    public Result foundNewMeeting() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();

        //check userId ,pptId,topic
        JsonResult resultJson;

        resultJson = checkUserId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        resultJson = checkPptId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        resultJson = checkTopic(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        String topic = params.get("topic")[0];
        Long pptId = Long.parseLong(params.get("pptId")[0]);
        Long userId = Long.parseLong(params.get("userId")[0]);
        resultJson = meetingService.foundNewMeeting(userId, pptId, topic);
        return ok(resultJson);
    }

    /**
     * 加入新的会议
     *
     * @return [description]
     */
    /*public Result joinMeeting() {
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        //check userId ,meetingId
        JsonResult resultJson;

        //检查用户Id
        resultJson = checkUserId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        //检查meetingId
        resultJson = checkMeetingId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        Long meetingId = Long.parseLong(params.get("meetingId")[0]);
        Long userId = Long.parseLong(params.get("userId")[0]);

        resultJson = meetingService.joinMeeting(userId, meetingId);

        return ok(resultJson);
    }     */

    /**
     * 获取用户所有自己发起的会议的列表
     *
     * @return
     */
   /* public Result getMyFoundedMeetings() {
        Map<String, String[]> params = request().queryString();

        JsonResult resultJson;

        //检查userId
        resultJson = checkUserId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        //获取参数
        Long userId = Long.parseLong(params.get("userId")[0]);

        ArrayNode foundedMeetingsArrayNode = meetingService
                .getMyFoundedMeetings(userId);
        resultJson = new JsonResult(true, foundedMeetingsArrayNode);
        return ok(resultJson);
    }    */


    /**
     * 获取指定会议的信息
     *
     * @return
     */
   /* public Result getMeetingInfo() {
        Map<String, String[]> params = request().queryString();

        JsonResult resultJson;

        //检查meetingId
        resultJson = checkMeetingId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        Long meetingId = Long.parseLong(params.get("meetingId")[0]);
        resultJson = meetingService.getMeetingInfo(meetingId);
        return ok(resultJson);
    }       */

    /**
     * 设置会议的直播PPT页码
     *
     * @param
     * @return
     */
    /*public Result setMeetingPageIndex() {
        Long meetingId;
        Long pageIndex;
        // 获取POST参数
        Map<String, String[]> params = request().body().asFormUrlEncoded();
        JsonResult resultJson;

        resultJson = checkMeetingId(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        resultJson = checkPageIndex(params);
        if (!resultJson.getStatusCode().equals(StatusCode.SUCCESS))
            return ok(resultJson);

        meetingId = Long.valueOf(params.get("meetingId")[0]);
        pageIndex = Long.valueOf(params.get("pageIndex")[0]);

        resultJson = meetingService.setMeetingPageIndex(meetingId, pageIndex);
        return ok(resultJson);
    }       */

    //数字匹配器
    Pattern patternNumbers = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 检查userId字段
     *
     * @param params
     * @return
     */
    JsonResult checkUserId(Map<String, String[]> params) {
        if (!params.containsKey("userId")) {
            return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
        }

        if (!patternNumbers.matcher(params.get("userId")[0]).matches())
            return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
        return new JsonResult(true);
    }

    /**
     * 检查meetingId字段
     *
     * @param params
     * @return
     */
    JsonResult checkMeetingId(Map<String, String[]> params) {
        if (!params.containsKey("meetingId")) {
            return new JsonResult(false, StatusCode.MEETING_ID_ERROR, "meetingId字段错误");
        }
        if (!patternNumbers.matcher(params.get("meetingId")[0]).matches())
            return new JsonResult(false, StatusCode.MEETING_ID_ERROR, "meetingId字段错误");
        return new JsonResult(true);
    }

    /**
     * 检查pageIndex字段
     *
     * @param params
     * @return
     */
    JsonResult checkPageIndex(Map<String, String[]> params) {
        if (!params.containsKey("pageIndex")) {
            return new JsonResult(false, StatusCode.MEETING_PAGEINDEX_ERROR, "pageIndex字段错误");
        }
        if (!patternNumbers.matcher(params.get("pageIndex")[0]).matches())
            return new JsonResult(false, StatusCode.MEETING_PAGEINDEX_ERROR, "pageIndex字段错误");
        return new JsonResult(true);
    }

    /**
     * 检查pptId字段
     *
     * @param params
     * @return
     */
    JsonResult checkPptId(Map<String, String[]> params) {
        if (!params.containsKey("pptId")) {
            return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
        }

        if (!patternNumbers.matcher(params.get("pptId")[0]).matches())
            return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
        return new JsonResult(true);
    }

    /**
     * 检查topic字段
     *
     * @param params
     * @return
     */
    JsonResult checkTopic(Map<String, String[]> params) {
        if (!params.containsKey("topic")) {
            return new JsonResult(false, StatusCode.MEETING_TOPIC_ERROR, "topic错误");
        }
        return new JsonResult(true);
    }
}
