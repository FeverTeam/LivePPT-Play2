package com.fever.liveppt.exception.common;

import com.fever.liveppt.exception.BasicException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-28
 * Time: 下午3:44
 * To change this template use File | Settings | File Templates.
 */
public class CommonException extends BasicException {
    public CommonException() {
        super(-1, "");
    }

    public CommonException(int retcode) {
        super(retcode, "");
    }

    public CommonException(int retcode, String message) {
        super(retcode, message);
    }

}
