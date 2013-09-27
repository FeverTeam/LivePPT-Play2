package com.fever.liveppt.utils;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-26
 * Time: 下午10:00
 * To change this template use File | Settings | File Templates.
 */
public class CacheAgent {

    public static String generateMeetingCacheKey(long meeetingId){
        return String.valueOf(meeetingId);
    }

}
