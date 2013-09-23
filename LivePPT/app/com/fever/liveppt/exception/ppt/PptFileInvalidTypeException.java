package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-6
 * Time: 上午12:45
 * To change this template use File | Settings | File Templates.
 */
public class PptFileInvalidTypeException extends PptException {
    public PptFileInvalidTypeException() {
        super(StatusCode.PPT_FILE_INVALID_TYPE, StatusCode.PPT_FILE_INVALID_TYPE_MESSAGE);
    }
}
