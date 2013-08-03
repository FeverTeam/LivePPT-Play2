package com.liveppt.utils.exception.params;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黎伟杰
 */
public class PasswordNotFoundException extends ParamsException{

    public PasswordNotFoundException(){
        super(StatusCode.USER_PASSWORD_NOT_FOUND);
    }

    public PasswordNotFoundException(int status){
        super(status);
    }

    public PasswordNotFoundException(int status,String gripe){
        super(status,gripe);
    }
}
