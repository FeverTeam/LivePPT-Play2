package com.liveppt.utils.exception.meeting;

import com.liveppt.utils.StatusCode;
import com.liveppt.utils.exception.ppt.PptException;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class MeetingIdErrorException extends MeetingException {

    public MeetingIdErrorException(){
        super(StatusCode.MEETING_ID_ERROR);
    }

    public MeetingIdErrorException(int status){
        this(status,"MEETING_ID_ERROR");
    }

    public MeetingIdErrorException(int status, String gripe){
        super(status,gripe);
    }

}
