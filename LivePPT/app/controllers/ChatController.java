package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.MeetingAgent;
import com.fever.liveppt.utils.TokenAgent;
import com.typesafe.plugin.RedisPlugin;
import play.Play;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.List;

import com.fever.liveppt.utils.MeetingAgent;

@URIPrefix("chat")
public class ChatController extends WAMPlayContoller {

    private static final String ERROR_RESPONSE_STR = "error";
    private static final String SUCCESS_RESPONSE_STR = "ok";

    private static final String PUBLISH_TYPE_NEW_CHAT = "newChat";
    private static final String PUBLISH_TYPE_RESET_CHAT = "resetChat";

    @onRPC("#say")
    public static String sayNewChat(String sessionID, JsonNode[] args) {
        if (args.length != 4) {
            return ERROR_RESPONSE_STR;
        }
        //提取参数
        String userEmail = args[0].asText();
        String token = args[1].asText();
        long meetingId = args[2].asLong();
        String chatText = args[3].asText();
        if (userEmail == null || token == null || meetingId == 0 || chatText == null) {
            return ERROR_RESPONSE_STR;
        }

        //验证
        if (!TokenAgent.isTokenValid(token, userEmail)) {
            return ERROR_RESPONSE_STR;
        }
        User user = User.find.where().eq("email", userEmail).findUnique();

        //生成chat topic
        String chatTopicUri = MeetingAgent.getOrCreateChatTopic(meetingId);

        //组装json
        JsonNode chatJson = Json.newObject()
                .put("publisherEmail", userEmail)
                .put("publisherDisplayName", user.displayname)
                .put("chatText", chatText);

        //推送chat消息
        WAMPlayServer.publish(chatTopicUri, resultJsonForPublish(PUBLISH_TYPE_NEW_CHAT, chatJson.toString()));

        //设入Redis
        String chatCacheKey = MeetingAgent.genMeetingChatCacheKey(meetingId);
        JedisPool jedisPool = Play.application().plugin(RedisPlugin.class).jedisPool();
        Jedis j = jedisPool.getResource();
        try {
            j.rpush(chatCacheKey, chatJson.toString());
        } finally {
            jedisPool.returnResource(j);
        }


        return SUCCESS_RESPONSE_STR;
    }

    @onRPC("#queryAll")
    public static String queryAllChats(String sessionID, JsonNode[] args) {
        if (args.length != 2) {
            return ERROR_RESPONSE_STR;
        }

        //提取参数
        long meetingId = args[0].asLong();
        if (meetingId == 0) {
            return ERROR_RESPONSE_STR;
        }
        String chatCacheKey = MeetingAgent.genMeetingChatCacheKey(meetingId);

        //检查meetingId的合法性并创建对应的chat topic
        String chatTopicUri = MeetingAgent.getOrCreateChatTopic(meetingId);
        if (chatTopicUri == null) {
            return ERROR_RESPONSE_STR;
        }

        JedisPool jedisPool = Play.application().plugin(RedisPlugin.class).jedisPool();
        Jedis j = jedisPool.getResource();
        List<String> chatList;
        try {
            chatList = j.lrange(chatCacheKey, 0, -1);
        } finally {
            jedisPool.returnResource(j);
        }


        //组装json
        ArrayNode chatArr = JsonNodeFactory.instance.arrayNode();
        for (String chat : chatList) {
            chatArr.add(chat);
        }
        JsonNode json = Json.newObject()
                .put("chatTopicUri", chatTopicUri)
                .put("chats", chatArr);

        return json.toString();
    }

    @onRPC("#reset")
    public static String resetChat(String sessionID, JsonNode[] args) {
        if (args.length != 1) {
            return ERROR_RESPONSE_STR;
        }

        //提取参数
        long meetingId = args[0].asLong();
        if (meetingId == 0) {
            return ERROR_RESPONSE_STR;
        }

        //清空会议对应的chat cache
        JedisPool jedisPool = Play.application().plugin(RedisPlugin.class).jedisPool();
        Jedis j = jedisPool.getResource();
        try {
            //删除所有对应页面的路径
            j.del(MeetingAgent.genMeetingChatCacheKey(meetingId));
        } finally {
            jedisPool.returnResource(j);
        }

        //推送reset消息
        WAMPlayServer.publish(
                MeetingAgent.genChatTopicName(meetingId),
                resultJsonForPublish(PUBLISH_TYPE_RESET_CHAT, null)
        );

        return SUCCESS_RESPONSE_STR;
    }

    //组装结果字符串
    private static JsonNode resultJsonForPublish(String type, String dataStr) {
        dataStr = (dataStr == null) ? "" : dataStr;
        return Json.newObject().put("type", type).put("data", dataStr);
    }

}
