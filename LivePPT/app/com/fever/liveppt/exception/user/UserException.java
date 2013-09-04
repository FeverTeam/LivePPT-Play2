package com.fever.liveppt.exception.user;

import com.fever.liveppt.exception.BasicException;


public class UserException extends BasicException {

    public UserException() {
        super(-1, "");
    }

    public UserException(int retcode) {
        super(retcode, "");
    }

    public UserException(int retcode, String message) {
        super(retcode, message);
    }
}
