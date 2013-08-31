package controllers;

import akka.actor.Cancellable;
import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.JsonResult;
import com.google.inject.Inject;
import org.codehaus.jackson.node.ObjectNode;
import play.Logger;
import play.cache.Cache;
import play.libs.Akka;
import play.libs.F.Callback;
import play.libs.F.Callback0;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;
import scala.concurrent.duration.Duration;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 有关Meeting的数据接口
 *
 * @author 梁博文
 */
public class MeetingController extends Controller {

    @Inject
    MeetingService meetingService;

    public Result foundNewMeeting() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();
        String topic = data.get("topic")[0];
        Long pptId = Long.parseLong(data.get("pptId")[0]);
        Long userId = User.genUserFromSession(ctx().session()).id;
        meetingService.foundNewMeeting(userId, pptId, topic);
        return ok("foundNewMeeting");
    }

    public Result deleteMeeting() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();
        Long meetingId = Long.parseLong(data.get("meetingId")[0]);
        meetingService.deleteMeeting(meetingId);
        return ok("deleteMeeting" + meetingId);
    }

    public Result quitMeeting() {
        JsonResult resultJson;

        Map<String, String[]> data = request().body().asFormUrlEncoded();
        //TUDO 检查参数
        Long meetingId = Long.parseLong(data.get("meetingId")[0]);
        Long userId = Long.parseLong(data.get("userId")[0]);
        resultJson = meetingService.quitMeeting(userId, meetingId);
        return ok(resultJson);
    }

/*	public Result quitMeeting(Long userId,Long meetingId) {
        JsonResult resultJson;
		//TUDO 检查参数
		resultJson = meetingService.quitMeeting(userId,meetingId);
		return ok(resultJson);
	}*/

    public Result setMeetingPage() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();
        Long meetingId = Long.parseLong(data.get("meetingId")[0]);
        Long currentPageIndex = Long.parseLong(data.get("currentPageIndex")[0]);
        String cacheKey = meetingId.toString();
        Logger.info(meetingId + "-" + currentPageIndex);
        Cache.set(cacheKey, currentPageIndex);
        return ok("setMeetingPage");
    }

    public static Result joinMeeting() {
        Map<String, String[]> data = request().body().asFormUrlEncoded();
        Long meetingId = Long.parseLong(data.get("inputMeetingId")[0]);
        User user = User.genUserFromSession(ctx().session());

        Meeting meeting = Meeting.find.byId(meetingId);
        ObjectNode result = Json.newObject();
        if (meeting == null) {
            result.put("success", false);
            result.put("message", "没有这个会议");
            return ok(result);
        }

        if (user == null) {
            result.put("success", false);
            result.put("message", "没有这个用户。");
            return ok(result);
        }

        List<Attender> attendings = user.attendents;
        boolean isAttended = false;
        for (Attender attending : attendings) {
            if (attending.meeting.id.equals(meeting.id)) {
                isAttended = true;
                break;
            }
        }

        if (!isAttended) {
            Attender newAttending = new Attender(meeting, user);
            newAttending.save();
        }

        result.put("success", true);
        return ok(result);
    }

    public static WebSocket<String> viewWebsocket() {
        return new WebSocket<String>() {

            String WS_TEMP_ID_KEY;

            @Override
            public void onReady(WebSocket.In<String> in,
                                final WebSocket.Out<String> out) {
                // For each event received on the socket,

                //WebSocket响应
                in.onMessage(new Callback<String>() {
                    @Override
                    public void invoke(String meetingIdStr) {
                        WS_TEMP_ID_KEY = UUID.randomUUID().toString();
                        Long meetingId = Long.parseLong(meetingIdStr);
                        Logger.info("WebSocket Started by meetingId="
                                + meetingIdStr);
                        final Long pptId = Meeting.find.byId(meetingId).ppt.id;
                        final String cacheKey = Long.toString(meetingId);

                        Cancellable cancellable = Akka
                                .system()
                                .scheduler()
                                .schedule(
                                        Duration.Zero(),
                                        Duration.create(10,
                                                TimeUnit.MILLISECONDS),
                                        new Runnable() {
                                            Long currentIndex;
                                            Long temp = (long) -1;

                                            @Override
                                            public void run() {
                                                // TODO Auto-generated method
                                                // stub
                                                currentIndex = (Long) Cache
                                                        .get(cacheKey);
                                                if (currentIndex == null) {
                                                    currentIndex = (long) 1;
                                                }
                                                if (!temp.equals(currentIndex)) {
                                                    temp = currentIndex;
                                                    Logger.info(pptId + "-"
                                                            + currentIndex);
                                                    out.write(pptId + "-"
                                                            + currentIndex);
                                                }
                                            }
                                        }, Akka.system().dispatcher());
                        //将该WebSocket连接对应的Akka Cancellable存入Cache，用于终止连接时停止定时任务
                        Cache.set(WS_TEMP_ID_KEY, cancellable);
                    }
                });//onMesaage

                //WebSocket关闭
                in.onClose(new Callback0() {
                    @Override
                    public void invoke() {
                        //从Cache获取该WebSocket连接对应的Akka Cancellable
                        Cancellable cancellable = (Cancellable) Cache
                                .get(WS_TEMP_ID_KEY);
                        //停止定时任务，释放资源
                        cancellable.cancel();
                    }
                });//onClose
            }
        };
    }
}
