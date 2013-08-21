package com.liveppt.utils.models;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * description
 * author 黎伟杰
 */
public class MeetingJson extends ObjectNode{

    public static String KEY_MEETING_ID = "meetingId";
    public static String KEY_USER_ID = "userId";
    public static String KEY_TOPIC = "topic";
    public static String KEY_PPT_ID = "pptId";

    /**
     * 初始化产生MeetingJson
     * @param userId
     * @param meetingId
     * @param pptId
     * last modified 黎伟杰
     */
    public MeetingJson( Long userId,Long meetingId, String topic,Long pptId){
        super(JsonNodeFactory.instance);
        setUserId(userId);
        setMeetingId(meetingId);
        setTopic(topic);
        setPptId(pptId);
    }

    /**
     * 初始化产生MeetingJson
     * @param meetingReader
     * last modified 黎伟杰
     */
    public MeetingJson(MeetingReader meetingReader){
        super(JsonNodeFactory.instance);
        //TODO 等待MeetingReader
    }

    /**
     * 为MeetingJson加入userId信息
     * @param userId
     * @return itselt
     * last modified 黎伟杰
     */
    public MeetingJson setUserId( Long userId){
        this.put(KEY_USER_ID,userId);
        return this;
    }

    /**
     * 为MeetingJson加入meetingId信息
     * @param meetingId
     * @return itselt
     * last modified 黎伟杰
     */
    public MeetingJson setMeetingId(Long meetingId){
        this.put(KEY_MEETING_ID,meetingId);
        return this;
    }

    /**
     * 从MeetingJson取出meetingId状态信息
     * @param
     * @return  meetingId
     * last modified 黎伟杰
     */
    public Long getMeetingId(){
        return this.get(KEY_MEETING_ID).asLong();
    }

    /**
     * 为MeetingJson加入topic信息
     * @param topic
     * @return  itself
     * last modified 黎伟杰
     */
    public MeetingJson setTopic(String topic){
        this.put(KEY_TOPIC,topic);
        return this;
    }

    /**
     * 从MeetingJson取出topic状态信息
     * @param
     * @return  topic
     * last modified 黎伟杰
     */
    public String getTopic(){
        return this.get(KEY_TOPIC).asText();
    }

    /**
     * 为MeetingJson加入pptId信息
     * @param pptId
     * @return  itself
     * last modified 黎伟杰
     */
    public MeetingJson setPptId(Long pptId){
        this.put(KEY_PPT_ID, pptId);
        return this;
    }

    /**
     * 从MeetingJson取出pptId信息
     * @param
     * @return  pptId
     * last modified 黎伟杰
     */
    public Long getPptId(){
        return this.get(KEY_PPT_ID).asLong();
    }

}