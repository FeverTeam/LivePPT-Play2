package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fever.liveppt.utils.MeetingAgent;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;

@URIPrefix("pageQuery")
public class PageQueryController extends WAMPlayContoller {

    private static final String blankJsonString = "{\"pageIndex\":0,\"topicUri\":\"\"}";

    @onRPC("#currentPageIndex")
    public static String query(String sessionID, JsonNode[] args) throws Exception {
        if (args.length != 1) {
            return blankJsonString;
        }

        //第一个参数为meetingId
        long meetingId = args[0].asLong();
        if (meetingId == 0) {
            return blankJsonString;
        }

        //检查meetingId的合法性并创建对应的页码topic
        String topicUri = MeetingAgent.getOrCreatePageTopic(meetingId);
        if (topicUri == null || topicUri.equals("")) {
            return blankJsonString;
        }

        //获取页码
        long pageIndex = MeetingAgent.getCurrentPageIndex(meetingId);

        //返回json字符串
        return "{\"pageIndex\":" + pageIndex + ",\"topicUri\":\"" + topicUri + "\"}";
    }


}
