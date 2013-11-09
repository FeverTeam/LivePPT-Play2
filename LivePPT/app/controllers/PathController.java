package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fever.liveppt.utils.MeetingAgent;
import com.typesafe.plugin.RedisPlugin;
import play.libs.Json;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import ws.wamplay.annotations.URIPrefix;
import ws.wamplay.annotations.onRPC;
import ws.wamplay.controllers.WAMPlayContoller;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.List;

import static com.fever.liveppt.utils.MeetingAgent.genMeetingPathCacheKey;
import static com.fever.liveppt.utils.MeetingAgent.genPathTopicName;

@URIPrefix("path")
public class PathController extends WAMPlayContoller {

    private static final String errResponseStr = "error";
    private static final String successResponseStr = "ok";
    private static final String blankJsonString = "{\"topicUri\":\"\"}";

    private static final String PUBLISH_TYPE_NEW_PATH = "newPath";
    private static final String PUBLISH_TYPE_RESET_PATH = "resetPath";

    /**
     * 获取pubsub所需的topic
     *
     * @param sessionID
     * @param args
     * @return
     * @throws Exception
     */
    @onRPC("#getTopic")
    public static String getTopic(String sessionID, JsonNode[] args) throws Exception {
        if (args.length != 1) {
            return blankJsonString;
        }

        //第一个参数为meetingId
        long meetingId = args[0].asLong();
        if (meetingId == 0) {
            return blankJsonString;
        }

        //检查meetingId的合法性并创建对应的页码topic
        String pathTopicUri = MeetingAgent.getOrCreatePathTopic(meetingId);
        if (pathTopicUri == null || pathTopicUri.equals("")) {
            return blankJsonString;
        }

        //返回json字符串
        return "{\"topicUri\":\"" + pathTopicUri + "\"}";
    }

    /**
     * 清空指定页面的所有笔迹
     *
     * @param sessionID
     * @param args
     * @return
     */
    @onRPC("#reset")
    public static String resetPath(String sessionID, JsonNode[] args) {
        if (args.length != 2) {
            return errResponseStr;
        }

        //提取参数
        long meetingId = args[0].asLong();
        long pageIndex = args[1].asLong();
        if (meetingId == 0 || pageIndex == 0) {
            return errResponseStr;
        }

        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        try {
            //删除所有对应页面的路径
            j.del(genMeetingPathCacheKey(meetingId, pageIndex));
        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }

        //推送reset消息
        WAMPlayServer.publish(
                genPathTopicName(meetingId),
                resultJsonForPublish(PUBLISH_TYPE_RESET_PATH, pageIndex, null)
        );

        return successResponseStr;
    }

    /**
     * 加入一条笔迹
     *
     * @param sessionID
     * @param args
     * @return 笔迹序号
     */
    @onRPC("#add")
    public static String addPath(String sessionID, JsonNode[] args) {
        if (args.length != 3) {
            return errResponseStr;
        }

        //提取参数
        long meetingId = args[0].asLong();
        long pageIndex = args[1].asLong();
        if (meetingId == 0 || pageIndex == 0 || args[2] == null) {
            return errResponseStr;
        }
        String jsonStr = args[2].toString();

        //生成cache key
        String pathCacheKey = genMeetingPathCacheKey(meetingId, pageIndex);

        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        long pathIndex = 0;
        try {
            Pipeline p = j.pipelined();
            p.rpush(pathCacheKey, jsonStr);
            Response<Long> pathIndexFuture = p.llen(pathCacheKey);
            p.sync();  //执行

            pathIndex = pathIndexFuture.get();

        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }


        if (pathIndex == 0) {
            return errResponseStr;
        } else {
            //推送新笔迹
            WAMPlayServer.publish(
                    genPathTopicName(meetingId),
                    resultJsonForPublish(PUBLISH_TYPE_NEW_PATH, pageIndex, jsonStr)
            );

            return String.valueOf(pathIndex);
        }

    }

    /**
     * 获取当前页面的所有笔迹
     *
     * @param sessionID
     * @param args
     * @return
     */
    @onRPC("#queryAll")
    public static String queryAllPath(String sessionID, JsonNode[] args) {
        if (args.length != 2) {
            return errResponseStr;
        }

        //提取参数
        long meetingId = args[0].asLong();
        long pageIndex = args[1].asLong();
        if (meetingId == 0 || pageIndex == 0) {
            return errResponseStr;
        }
        String pathCacheKey = genMeetingPathCacheKey(meetingId, pageIndex);

        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        List<String> pathList;
        try {
            pathList = j.lrange(pathCacheKey, 0, -1);
        } finally {
            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }

        return convertListToString(pathList);
    }

    /**
     * 查询指定序号的笔迹
     *
     * @param sessionID
     * @param args
     * @return
     */
    @onRPC("#query")
    public static String queryPath(String sessionID, JsonNode[] args) {
        if (args.length != 3) {
            return errResponseStr;
        }

        //提取参数
        long meetingId = args[0].asLong();
        long pageIndex = args[1].asLong();
        if (meetingId == 0 || pageIndex == 0 || !args[2].isArray()) {
            return errResponseStr;
        }
        ArrayNode arrJson = (ArrayNode) args[2];
        String pathCacheKey = genMeetingPathCacheKey(meetingId, pageIndex);

        Jedis j = play.Play.application().plugin(RedisPlugin.class).jedisPool().getResource();
        Response<String> strResponseArr[] = new Response[arrJson.size()];

        try {
        } finally {
            Pipeline p = j.pipelined();
            int targetIndex;
            for (int i = 0; i < arrJson.size(); i++) {
                targetIndex = arrJson.get(i).asInt();
                strResponseArr[i] = p.lindex(pathCacheKey, targetIndex - 1);
            }
            p.sync(); //执行


            play.Play.application().plugin(RedisPlugin.class).jedisPool().returnResource(j);
        }


        return convertResponseArrayToString(strResponseArr);
    }

    //组装结果字符串
    private final static JsonNode resultJsonForPublish(String type, long pageIndex, String dataStr) {
        dataStr = (dataStr == null) ? "" : dataStr;
        return Json.newObject().put("type", type).put("pageIndex", pageIndex).put("data", dataStr);
    }

    private final static String convertListToString(List<String> StringList) {
        StringBuilder sb = new StringBuilder("[");
        for (String path : StringList) {
            sb.append(path).append(",");
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");

        return sb.toString();
    }

    private final static String convertResponseArrayToString(Response<String>[] strResponseArr) {
        StringBuilder sb = new StringBuilder("[");
        String tmpStr;
        for (int i = 0; i < strResponseArr.length; i++) {
            tmpStr = strResponseArr[i].get();
            if (tmpStr == null) {
                sb.append("[],");
            } else {
                sb.append(tmpStr).append(",");
            }
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");

        return sb.toString();
    }

}
