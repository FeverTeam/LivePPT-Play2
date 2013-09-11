package com.fever.liveppt.exception.meeting;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-11
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public class AttendingExistedException extends MeetingException {
    public AttendingExistedException(){
        super(StatusCode.ATTENDING_EXISTED,StatusCode.ATTENDING_EXISTED_MESSAGE);
    }
}
