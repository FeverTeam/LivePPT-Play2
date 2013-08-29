package com.fever.liveppt.utils.exception.user;

import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.exception.UserException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-28
 * Time: 下午3:46
 * To change this template use File | Settings | File Templates.
 */
public class UserExcistedException extends UserException {

    /**
     * 构造方法
     */
    public UserExcistedException() {
        super(StatusCode.USER_EXISTED);
    }

    public UserExcistedException(int retcode) {
        super(retcode);
    }

    public UserExcistedException(String message) {
        super(StatusCode.INVALID_PARAMS, message);
    }

    public UserExcistedException(int retcode, String message) {
        super(retcode, message);
    }
}
