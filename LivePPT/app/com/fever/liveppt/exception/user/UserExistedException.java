package com.fever.liveppt.exception.user;

import com.fever.liveppt.utils.StatusCode;

public class UserExistedException extends UserException {

    public UserExistedException() {
        super(StatusCode.USER_EXISTED, StatusCode.USER_EXISTED_MESSAGE);
    }

    public UserExistedException(int retcode) {
        super(retcode);
    }

    public UserExistedException(String message) {
        super(StatusCode.INVALID_PARAMS, message);
    }

    public UserExistedException(int retcode, String message) {
        super(retcode, message);
    }
}
