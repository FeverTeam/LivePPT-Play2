package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-4
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class PptNotExistedException extends PptException {
    public PptNotExistedException() {
        super(StatusCode.PPT_NOT_EXISTED, StatusCode.PPT_NOT_EXISTED_MESSAGE);
    }
}
