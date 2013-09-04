package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-5
 * Time: 上午12:29
 * To change this template use File | Settings | File Templates.
 */
public class PptNotConvertedException extends PptException {
    public PptNotConvertedException() {
        super(StatusCode.PPT_NOT_CONVERTED, StatusCode.PPT_NOT_CONVERTED_MESSAGE);
    }
}
