package com.liveppt.utils.exception.meeting;

import com.liveppt.utils.StatusCode;
import com.liveppt.utils.exception.ppt.PptException;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class MeetingPermissionDenyException extends MeetingException {

    public MeetingPermissionDenyException(){
        super(StatusCode.MEETING_PERMISSION_DENY);
    }

    public MeetingPermissionDenyException(int status){
        this(status,"MEETING_PERMISSION_DENY");
    }

    public MeetingPermissionDenyException(int status, String gripe){
        super(status,gripe);
    }

}
