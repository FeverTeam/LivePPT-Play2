package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-8
 * Time: 下午2:27
 * To change this template use File | Settings | File Templates.
 */
public class PptNotSelfOwnException extends PptException {
    public PptNotSelfOwnException() {
        super(StatusCode.PPT_NOT_SELF_OWN, StatusCode.PPT_NOT_SELF_OWN_MESSAGE);
    }
}
