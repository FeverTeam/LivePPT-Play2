package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黄梓财
 */
public class NewPasswordNotFoundException extends UserException {

    public NewPasswordNotFoundException(){
        super(StatusCode.USER_NEW_PASSWORD_NOT_FOUND);
    }

    public NewPasswordNotFoundException(int status){
        super(status);
    }

    public NewPasswordNotFoundException(int status,String gripe){
        super(status,gripe);
    }
}
