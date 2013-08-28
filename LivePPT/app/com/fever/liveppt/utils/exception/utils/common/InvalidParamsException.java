package com.fever.liveppt.utils.exception.utils.common;

import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.exception.CommonException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-28
 * Time: 下午4:04
 * To change this template use File | Settings | File Templates.
 */
public class InvalidParamsException extends CommonException {

    /**
     * 构造方法
     */
    public InvalidParamsException() {
        super(StatusCode.INVALID_PARAMS, StatusCode.INVALID_PARAMS_MESSAGE);
    }

    public InvalidParamsException(int retcode) {
        super(retcode);
    }

    public InvalidParamsException(String message) {
        super(StatusCode.INVALID_PARAMS, message);
    }

    public InvalidParamsException(int retcode, String message) {
        super(retcode, message);
    }
}
