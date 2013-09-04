package com.fever.liveppt.exception.user;

import com.fever.liveppt.utils.StatusCode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-29
 * Time: 下午11:43
 * To change this template use File | Settings | File Templates.
 */
public class UserNotExistedException extends UserException {

    public UserNotExistedException() {
        super(StatusCode.USER_NOT_EXISTED, StatusCode.USER_NOT_EXISTED_MESSAGE);
    }

    public UserNotExistedException(int retcode) {
        super(retcode);
    }

    public UserNotExistedException(String message) {
        super(StatusCode.USER_NOT_EXISTED, message);
    }

    public UserNotExistedException(int retcode, String message) {
        super(retcode, message);
    }


}
