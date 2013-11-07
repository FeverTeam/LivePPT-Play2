package com.fever.liveppt.utils;

import com.fever.liveppt.models.Meeting;
import play.cache.Cache;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.concurrent.Callable;

public class MeetingAgent {

    public static final String PAGE_TOPIC_URI_PREFIX = "pageTopic";

    public static String genMeetingPageCacheKey(long meeetingId) {
        return "meeting" + meeetingId;
    }

    public static String getOrCreatePageTopic(long meetingId) {
        //是否存在该会议
        if (Meeting.find.byId(meetingId) == null) {
            return "";
        }

        //建立会议页码topic并返回对应uri
        String topicUri = genPageTopic(meetingId);
        if (!WAMPlayServer.isTopic(topicUri)) {
            //topic不存在，新增topic
            WAMPlayServer.addTopic(topicUri);
        }
        return topicUri;
    }

    public static long getCurrentPageIndex(long meetingId) {
        try {
            return Cache.getOrElse(genMeetingPageCacheKey(meetingId), pageOne(), 21600);
        } catch (Exception e) {
            return 1;
        }
    }

    public static String genPageTopic(long meetingId) {
        return PAGE_TOPIC_URI_PREFIX + "#meeting" + meetingId;
    }

    private static final Callable<Long> pageOne() {
        return new Callable<Long>() {
            @Override
            public Long call() {
                return (long) 1;
            }
        };
    }

}
