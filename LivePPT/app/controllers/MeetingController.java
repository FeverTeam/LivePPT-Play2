
package controllers;

import akka.actor.Cancellable;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.exception.meeting.AttendingExistedException;
import com.fever.liveppt.exception.meeting.MeetingException;
import com.fever.liveppt.exception.meeting.MeetingNotExistedException;
import com.fever.liveppt.exception.meeting.MeetingPermissionDenyException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.ppt.PptPageOutOfRangeException;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.*;
import com.google.inject.Inject;
import play.Logger;
import play.cache.Cache;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @version : v1.00
 * @Description : 会议controller 提供给前端以及手机端会议操作的接口
 */
public class MeetingController extends Controller {
    @Inject
    MeetingService meetingService;

    /**
     * 会议同步PPT
     *
     * @return
     */
    public static WebSocket<String> viewWebsocket() {
        return new WebSocket<String>() {

            String WS_TEMP_ID_KEY;
            Long meetingId;
            Long pptId;
            String cacheKey;

            @Override
            public void onReady(WebSocket.In<String> in,
                                final WebSocket.Out<String> out) {
                // For each event received on the socket,

                //WebSocket响应
                in.onMessage(new F.Callback<String>() {
                    @Override
                    public void invoke(String meetingIdStr) {
                        WS_TEMP_ID_KEY = UUID.randomUUID().toString();
                        meetingId = Long.parseLong(meetingIdStr);
                        Logger.info("WebSocket with meetingId:" + meetingIdStr);
                        pptId = Meeting.find.byId(meetingId).ppt.id;
                        cacheKey = MeetingAgent.genMeetingPageCacheKey(meetingId);

                        Cancellable cancellable = Akka.system().scheduler().schedule(
                                Duration.Zero(),
                                Duration.create(10,
                                        TimeUnit.MILLISECONDS),
                                new Runnable() {
                                    Long currentPageIndex = (long) -1;
                                    Long lastPageIndex = (long) -1;

                                    @Override
                                    public void run() {
                                        if (currentPageIndex == -1) {
                                            //第一次进入websocket,从数据库中获取当前页码
                                            Meeting meeting = Meeting.find.byId(meetingId);
                                            if (meeting != null && meeting.currentPageIndex > 0) {
                                                currentPageIndex = meeting.currentPageIndex;
                                            } else {
                                                currentPageIndex = (long) 1;
                                            }
                                            lastPageIndex = currentPageIndex;
                                            Logger.debug("websocket meetingId:" + meetingId + " page:" + currentPageIndex);
                                            out.write(pptId + "-" + currentPageIndex);
                                        } else {
                                            // stub
                                            currentPageIndex = (Long) Cache.get(cacheKey);
                                            if (currentPageIndex == null) {
                                                currentPageIndex = (long) 1;
                                            }
                                            if (!lastPageIndex.equals(currentPageIndex)) {
                                                lastPageIndex = currentPageIndex;
                                                Logger.debug("websocket meetingId:" + meetingId + " page:" + currentPageIndex);
                                                out.write(pptId + "-" + currentPageIndex);
                                            }
                                        }
                                    }
                                }, Akka.system().dispatcher());
                        //将该WebSocket连接对应的Akka Cancellable存入Cache，用于终止连接时停止定时任务
                        Cache.set(WS_TEMP_ID_KEY, cancellable);
                    }
                });//onMesaage

                //WebSocket关闭
                in.onClose(new F.Callback0() {
                    @Override
                    public void invoke() {
                        //从Cache获取该WebSocket连接对应的Akka Cancellable
                        Cancellable cancellable = (Cancellable) Cache.get(WS_TEMP_ID_KEY);
                        //停止定时任务，释放资源
                        cancellable.cancel();
                    }
                });//onClose
            }
        };
    }

    /**
     * 发起新会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     * @throws PptNotExistedException
     * @throws MeetingPermissionDenyException
     */
    public Result createMeeting() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "pptId")) {
                throw new InvalidParamsException();
            }

            if (!ControllerUtils.isFieldNotNull(params, "topic")) {
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
            resultJson = meetingService.createMeeting(userEmail, pptId, topic);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 删除用户自己发起的会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     * @throws MeetingNotExistedException
     * @throws MeetingPermissionDenyException
     */
    public Result deleteMeeting() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            //  String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
                throw new InvalidParamsException();
            }


            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            //删除
            meetingService.deleteMeeting(userEmail, meetingId);
            resultJson = ResultJson.simpleSuccess();
            //若返回JSON为空，设为位置错误
            // resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 修改用户自己发起的会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     * @throws MeetingNotExistedException
     * @throws MeetingPermissionDenyException
     */
    public Result updateMeeting() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "weijie@gmail.com";
            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
                throw new InvalidParamsException();
            }

            if (!ControllerUtils.isFieldNotNull(params, "pptId")) {
                throw new InvalidParamsException();
            }

            if (!ControllerUtils.isFieldNotNull(params, "topic")) {
                throw new InvalidParamsException();
            }
            //获取参数
            Long meetingId = Long.valueOf(params.get("meetingId")[0]);
            if (meetingId == null) {
                //长整型转换失败
                throw new InvalidParamsException();
            }

            Long pptId = Long.valueOf(params.get("pptId")[0]);
            if (pptId == null) {
                //长整形转换失败
                throw new InvalidParamsException();
            }

            String topic = params.get("topic")[0];

            //修改会议
            resultJson = meetingService.updateMeeting(userEmail, meetingId, pptId, topic);

            //若返回JSON为空，设为位置错误
            resultJson = (resultJson == null) ? new ResultJson(new CommonException(StatusCode.UNKONWN_ERROR, "unknown error")) : resultJson;

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        } catch (PptNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 加入观看指定会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws
     */
    public Result joinMeeting() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "bowen@gmail.com";
            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
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
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (AttendingExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 退出观看指定会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     * @throws MeetingException
     */
    public Result quitMeeting() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "bowen@gmail.com";
            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
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
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 设置会议的PPT页码
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     * @throws MeetingNotExistedException
     * @throws PptPageOutOfRangeException
     * @throws MeetingPermissionDenyException
     */
    public Result setPage() {
        ResultJson resultJson;
        try {
            //验证Token并提取userEmail
            String userEmail = TokenAgent.validateTokenFromHeader(request());

            //获取POST参数
            Map<String, String[]> params = request().body().asFormUrlEncoded();

            //检查必须的参数是否存在
            if (!ControllerUtils.isFieldNotNull(params, "meetingId")) {
                throw new InvalidParamsException();
            }

            if (!ControllerUtils.isFieldNotNull(params, "pageIndex")) {
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
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        } catch (PptPageOutOfRangeException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingPermissionDenyException e) {
            resultJson = new ResultJson(e);
        }
        // resultJson = new ResultJson(e);
        // }

        return ok(resultJson.objectNode);
    }

    /**
     * 获取用户所有自己发起的会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     */
    public Result getMyFoundedMeetings() {
        ResultJson resultJson;
        try {
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "weijie@gmail.com";
            //获取ppt
            List<Meeting> meetingList = meetingService.getMyFoundedMeetings(userEmail);

            //组装MEETING信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Meeting meeting : meetingList) {
                pptInfoArraryNode.add(meeting.toMyMeetingJson());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }

    /**
     * 获取用户所有观看的会议
     *
     * @return
     * @throws TokenInvalidException
     * @throws InvalidParamsException
     */
    public Result getMyAttendingMeeting() {
        ResultJson resultJson;
        try {
            String userEmail = TokenAgent.validateTokenFromHeader(request());
            // String userEmail = "bowen@gmail.com";
            //获取ppt
            List<Meeting> meetingList = meetingService.getMyAttendingMeetings(userEmail);

            //组装MEETING信息JSON数组
            ArrayNode pptInfoArraryNode = new ArrayNode(JsonNodeFactory.instance);
            for (Meeting meeting : meetingList) {
                pptInfoArraryNode.add(meeting.toMeetingJson());
            }

            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, pptInfoArraryNode);

        } catch (TokenInvalidException e) {
            resultJson = new ResultJson(e);
        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        }

        return ok(resultJson.objectNode);
    }

    /**
     * 获取指定会议信息
     *
     * @return
     * @throws InvalidParamsException
     * @throws MeetingNotExistedException
     */
    public Result getMeetingInfo() {
        ResultJson resultJson;
        try {
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

        } catch (InvalidParamsException e) {
            resultJson = new ResultJson(e);
        } catch (MeetingNotExistedException e) {
            resultJson = new ResultJson(e);
        }
        return ok(resultJson.objectNode);
    }
}
