package com.fever.liveppt.exception.common;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-4
 * Time: 下午9:36
 * To change this template use File | Settings | File Templates.
 */
public class UnknownErrorException extends CommonException {
    public UnknownErrorException(){
        super(StatusCode.UNKONWN_ERROR, StatusCode.UNKONWN_ERROR_MESSAGE);
    }
}
