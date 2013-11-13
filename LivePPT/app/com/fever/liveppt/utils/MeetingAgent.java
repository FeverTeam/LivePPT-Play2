package com.fever.liveppt.utils;

import com.fever.liveppt.models.Meeting;
import play.cache.Cache;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.concurrent.Callable;

public class MeetingAgent {

    public static final String PAGE_TOPIC_URI_PREFIX = "pageTopic";
    public static final String PATH_TOPIC_URI_PREFIX = "pathTopic";
    public static final String CHAT_TOPIC_URI_PREFIX = "chatTopic";

    //生成page cache key
    public static String genMeetingPageCacheKey(long meeetingId) {
        return "meeting" + meeetingId;
    }

    //生成path cache key
    public static String genMeetingPathCacheKey(long meeetingId, long pageIndex) {
        StringBuilder sb = new StringBuilder("path.meeting");
        return sb.append(meeetingId).append(".page").append(pageIndex).toString();
    }

    //生成chat cache key
    public static String genMeetingChatCacheKey(long meeetingId) {
        StringBuilder sb = new StringBuilder("chat.meeting");
        return sb.append(meeetingId).toString();
    }

    public static String getOrCreatePageTopic(long meetingId) {
        //是否存在该会议
        if (Meeting.find.byId(meetingId) == null) {
            return null;
        }

        //建立会议页码topic并返回对应uri
        String pageTopicUri = genPageTopicName(meetingId);
        if (!WAMPlayServer.isTopic(pageTopicUri)) {
            //topic不存在，新增topic
            WAMPlayServer.addTopic(pageTopicUri);
        }
        return pageTopicUri;
    }

    public static String getOrCreatePathTopic(long meetingId) {
        //是否存在该会议
        if (Meeting.find.byId(meetingId) == null) {
            return null;
        }

        //建立笔迹topic并返回对应uri
        String pathTopicUri = genPathTopicName(meetingId);
        if (!WAMPlayServer.isTopic(pathTopicUri)) {
            //topic不存在，新增topic
            WAMPlayServer.addTopic(pathTopicUri);
        }
        return pathTopicUri;
    }

    public static String getOrCreateChatTopic(long meetingId) {
        //是否存在该会议
        if (Meeting.find.byId(meetingId) == null) {
            return null;
        }

        //建立笔迹topic并返回对应uri
        String chatTopicUri = genChatTopicName(meetingId);
        if (!WAMPlayServer.isTopic(chatTopicUri)) {
            //topic不存在，新增topic
            WAMPlayServer.addTopic(chatTopicUri);
        }
        return chatTopicUri;
    }

    public static long getCurrentPageIndex(long meetingId) {
        try {
            return Cache.getOrElse(genMeetingPageCacheKey(meetingId), pageOne(), 21600);
        } catch (Exception e) {
            return 1;
        }
    }

    public static String genPageTopicName(long meetingId) {
        return PAGE_TOPIC_URI_PREFIX + "#meeting" + meetingId;
    }

    public static String genPathTopicName(long meetingId) {
        return PATH_TOPIC_URI_PREFIX + "#meeting" + meetingId;
    }

    public static String genChatTopicName(long meetingId) {
        return CHAT_TOPIC_URI_PREFIX + "#meeting" + meetingId;
    }

    private static Callable<Long> pageOne() {
        return new Callable<Long>() {
            @Override
            public Long call() {
                return (long) 1;
            }
        };
    }

}
