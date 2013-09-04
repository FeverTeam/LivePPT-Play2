package com.fever.liveppt.exception.common;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-5
 * Time: 上午12:42
 * To change this template use File | Settings | File Templates.
 */
public class InternalErrorException extends CommonException {
    public InternalErrorException(){
        super(StatusCode.INTERNAL_ERROR, StatusCode.INTERNAL_ERROR_MESSAGE);
    }
}
