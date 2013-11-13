package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.TokenAgent;
import play.libs.Json;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;
import ws.wamplay.controllers.WAMPlayServer;

import static com.fever.liveppt.utils.MeetingAgent.getOrCreateChatTopic;

@URIPrefix("chat")
public class ChatController extends WAMPlayContoller {

    private static final String ERROR_STR = "error";
    private static final String SUCCESS_STR = "ok";

    @onRPC("#say")
    public static String sayNewChat(String sessionID, JsonNode[] args) {
        if (args.length != 4) {
            return ERROR_STR;
        }
        //提取参数
        String userEmail = args[0].asText();
        String token = args[1].asText();
        long meetingId = args[2].asLong();
        String chatText = args[3].asText();
        if (userEmail == null || token == null || meetingId == 0 || chatText == null) {
            return ERROR_STR;
        }

        //验证
        if (!TokenAgent.isTokenValid(token, userEmail)) {
            return ERROR_STR;
        }
        User user = User.find.where().eq("email", userEmail).findUnique();


        //生成chat topic
        String chatTopicUri = getOrCreateChatTopic(meetingId);
        JsonNode json = Json.newObject()
                .put("publisherEmail", userEmail)
                .put("publisherDisplayName", user.displayname)
                .put("chatText", chatText);

        //推送chat消息
        WAMPlayServer.publish(chatTopicUri, json);

        return SUCCESS_STR;
    }

}
