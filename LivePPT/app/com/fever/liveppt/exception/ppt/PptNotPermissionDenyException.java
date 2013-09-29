package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-9-28
 * Time: 下午9:37
 * To change this template use File | Settings | File Templates.
 */
public class PptNotPermissionDenyException extends PptException {
    public PptNotPermissionDenyException()
    {
        super(StatusCode.PPT_NOT_PERMISSION_DENY,StatusCode.PPT_NOT_PERMISSION_DENY_MESSAGE);
    }
}
