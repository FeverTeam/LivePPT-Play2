package com.fever.liveppt.exception.meeting;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-10
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public class MeetingNotExistedException extends MeetingException {

    public MeetingNotExistedException() {
        super(StatusCode.MEETING_NOT_EXISTED, StatusCode.MEETING_NOT_EXISTED_MESSAGE);
    }
}
