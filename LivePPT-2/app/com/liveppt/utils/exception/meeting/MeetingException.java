package com.liveppt.utils.exception.meeting;

import com.liveppt.utils.exception.LivePPTException;

/**
 * Date: 13-8-18
 * Time: 下午5:38
 *
 * @author 黎伟杰
 */
public class MeetingException extends LivePPTException{

    public MeetingException(){
        super();
    }

    public MeetingException(int status){
        super(status);
    }

    public MeetingException(int status, String gripe){
        super(status,gripe);
    }

}
