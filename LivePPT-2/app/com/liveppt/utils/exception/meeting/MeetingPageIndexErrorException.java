package com.liveppt.utils.exception.meeting;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class MeetingPageIndexErrorException extends MeetingException {

    public MeetingPageIndexErrorException(){
        super(StatusCode.MEETING_PAGEINDEX_ERROR);
    }

    public MeetingPageIndexErrorException(int status){
        this(status,"MEETING_PAGEINDEX_ERROR");
    }

    public MeetingPageIndexErrorException(int status, String gripe){
        super(status,gripe);
    }

}
