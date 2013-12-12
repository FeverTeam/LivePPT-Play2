package com.fever.liveppt.constant;


public class Expiration {

    //默认会议页码缓存21600秒（即6小时）
    public final static int DEFAULT_MEETING_PAGE_CACHE_EXPIRATION = 21600;

    //默认PPT页面缓存3600秒（即2小时）
    public final static int DEFAULT_PPT_PAGE_IMAGE_CACHE_EXPIRATION = 7200;

    //默认会议path缓存3600秒（即2小时）
    public static final int DEFAULT_PATH_CACHE_EXPIRATION = 7200;
}
