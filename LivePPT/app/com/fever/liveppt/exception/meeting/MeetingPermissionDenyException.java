package com.fever.liveppt.exception.meeting;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-10
 * Time: 上午11:09
 * To change this template use File | Settings | File Templates.
 */
public class MeetingPermissionDenyException extends MeetingException{
    public MeetingPermissionDenyException()
    {
        super(StatusCode.MEETING_PERMISSION_DENY,StatusCode.MEETING_PERMISSION_DENY_MESSAGE);
    }
}
