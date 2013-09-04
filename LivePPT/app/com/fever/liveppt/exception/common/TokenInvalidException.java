package com.fever.liveppt.exception.common;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-4
 * Time: 下午2:14
 * To change this template use File | Settings | File Templates.
 */
public class TokenInvalidException extends CommonException {
    public TokenInvalidException(int retcode, String message) {
        super(retcode, message);
    }

    public TokenInvalidException() {
        this(StatusCode.INVALID_TOKEN, StatusCode.INVALID_TOKEN_MESSAGE);
    }
}
