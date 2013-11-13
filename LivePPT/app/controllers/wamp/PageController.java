package controllers.wamp;

import com.fasterxml.jackson.databind.JsonNode;
import com.fever.liveppt.constant.Expiration;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.MeetingAgent;
import com.fever.liveppt.utils.TokenAgent;
import play.cache.Cache;
import play.libs.Json;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;
import ws.wamplay.controllers.WAMPlayServer;

import static com.fever.liveppt.constant.WampConstant.ERROR_RESPONSE_STR;
import static com.fever.liveppt.constant.WampConstant.SUCCESS_RESPONSE_STR;
import static com.fever.liveppt.utils.MeetingAgent.genMeetingPageCacheKey;
import static com.fever.liveppt.utils.MeetingAgent.getOrCreatePageTopic;

@URIPrefix("page")
public class PageController extends WAMPlayContoller {

    private static final String blankJsonString = "{\"pageIndex\":0,\"topicUri\":\"\"}";

    @onRPC("#set")
    public static String setPage(String sessionID, JsonNode[] args) {
        if (args.length != 4) {
            return ERROR_RESPONSE_STR;
        }
        String userEmail = args[0].asText();
        String token = args[1].asText();
        long meetingId = args[2].asLong();
        long pageIndex = args[3].asLong();
        if (userEmail == null || token == null || meetingId == 0 || pageIndex == 0) {
            return ERROR_RESPONSE_STR;
        }

        //验证token
        if (!TokenAgent.isTokenValid(token, userEmail)) {
            return ERROR_RESPONSE_STR;
        }

        //检查会议是否控制者所发起
        Meeting meeting = Meeting.find.byId(meetingId);
        User user = User.find.where().eq("email", userEmail).findUnique();
        if (meeting == null || user == null || !meeting.founder.id.equals(user.id)) {
            return ERROR_RESPONSE_STR;
        }

        //更新Cache中的页码
        String meetingCacheKey = genMeetingPageCacheKey(meetingId);
        Cache.set(meetingCacheKey, pageIndex, Expiration.DEFAULT_MEETING_PAGE_CACHE_EXPIRATION);

        //向wamp对应topic发布页码更新
        WAMPlayServer.publish(getOrCreatePageTopic(meetingId), Json.toJson(pageIndex));

        return SUCCESS_RESPONSE_STR;
    }

    @onRPC("#currentIndex")
    public static String query(String sessionID, JsonNode[] args) throws Exception {
        if (args.length != 1) {
            return blankJsonString;
        }

        //第一个参数为meetingId
        long meetingId = args[0].asLong();
        if (meetingId == 0) {
            return blankJsonString;
        }

        //检查meetingId的合法性并创建对应的page topic
        String pageTopicUri = MeetingAgent.getOrCreatePageTopic(meetingId);
        if (pageTopicUri == null) {
            return blankJsonString;
        }

        //获取页码
        long pageIndex = MeetingAgent.getCurrentPageIndex(meetingId);

        //返回json字符串
        return Json.newObject()
                .put("pageIndex", pageIndex)
                .put("pageTopicUri", pageTopicUri)
                .toString();
    }

}
