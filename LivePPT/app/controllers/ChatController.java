package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.MeetingAgent;
import com.fever.liveppt.utils.TokenAgent;
import com.typesafe.plugin.RedisPlugin;
import play.Logger;
import play.Play;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.List;

@URIPrefix("chat")
public class ChatController extends WAMPlayContoller {

    private static final String ERROR_RESPONSE_STR = "error";
    private static final String SUCCESS_RESPONSE_STR = "ok";
    private static final String PUBLISH_TYPE_NEW_CHAT = "newChat";
    private static final String PUBLISH_TYPE_RESET_CHAT = "resetChat";

    @onRPC("#say")
    public static String sayNewChat(String sessionID, JsonNode[] args) {
        if (args.length != 5) {
            return ERROR_RESPONSE_STR;
        }
        //提取参数
        String userEmail = args[0].asText();
        String token = args[1].asText();
        long meetingId = args[2].asLong();
        String chatText = args[3].asText();
        long time = args[4].asLong();
        if (userEmail == null || token == null || meetingId == 0 || chatText == null || time == 0) {
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
                .put("chatText", chatText)
                .put("time", time);

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
        if (args.length != 1) {
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
        List<String> chatList = null;
        try {
            chatList = j.lrange(chatCacheKey, 0, -1);
        } catch (Exception e) {
            Logger.info(e.toString());
        } finally {
            jedisPool.returnResource(j);
        }


        try {
            //组装json
            /*
            StringBuilder chatArrStr = new StringBuilder("[");

            if (chatList != null) {
                for (String chat : chatList) {
                    chatArrStr.append(chat).append(",");
                }
                if (chatArrStr.length() > 1) {
                    chatArrStr.deleteCharAt(chatArrStr.length() - 1);
                }
            }
            chatArrStr.append("]");
            */
            ArrayNode chatsArr = JsonNodeFactory.instance.arrayNode();
            JsonNode objNode;
            for (String chat : chatList) {
                objNode = Json.parse(chat);
                chatsArr.add(objNode);
            }
            if (chatsArr==null){
                Logger.info("chatsArr null");
            }
            else {Logger.info("chatsArr size"+chatsArr.size());}

            ObjectNode json = Json.newObject();
            json.put("chatTopicUri", chatTopicUri)
//                    .put("chats", chatArrStr.toString());
                    .put("chats", chatsArr);

            Logger.info(json.toString());

            return json.toString();
        } catch (Exception e) {
            Logger.info(e.toString());

            return ERROR_RESPONSE_STR;
        }


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
            //删除对应会议的chat cache
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
