package com.fever.liveppt.exception.meeting;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-17
 * Time: 下午8:05
 * To change this template use File | Settings | File Templates.
 */
public class MeetingNotAttendedException extends MeetingException{
   public  MeetingNotAttendedException()
    {
        super(StatusCode.NOT_ATTENDED,StatusCode.NOT_ATTENDED_MESSAGE);
    }
}
