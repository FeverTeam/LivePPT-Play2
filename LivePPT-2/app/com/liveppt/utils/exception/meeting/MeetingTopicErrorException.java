package com.liveppt.utils.exception.meeting;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class MeetingTopicErrorException extends MeetingException {

    public MeetingTopicErrorException(){
        super(StatusCode.MEETING_TOPIC_ERROR);
    }

    public MeetingTopicErrorException(int status){
        this(status,"MEETING_TOPIC_ERROR");
    }

    public MeetingTopicErrorException(int status, String gripe){
        super(status,gripe);
    }

}
