package com.fever.liveppt.exception.meeting;

import com.fever.liveppt.exception.BasicException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-10
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public class MeetingException extends BasicException {
    public MeetingException(int retcode, String message) {
        super(retcode, message);
    }

}
