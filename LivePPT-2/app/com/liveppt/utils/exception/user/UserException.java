package com.liveppt.utils.exception.user;

import com.liveppt.utils.exception.LivePPTException;

/**
 * description
 * author 黎伟杰
 */
public class UserException extends LivePPTException {

    public UserException(){
        super();
    }

    public UserException(int status){
        super(status);
    }

    public UserException(int status, String gripe){
        super(status,gripe);
    }
}
