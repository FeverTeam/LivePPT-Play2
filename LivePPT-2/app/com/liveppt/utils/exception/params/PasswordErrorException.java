package com.liveppt.utils.exception.params;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黄梓财
 */
public class PasswordErrorException extends ParamsException{

    public PasswordErrorException(){
        super(StatusCode.USER_PASSWORD_ERROR);
    }

    public PasswordErrorException(int status){
        super(status);
    }

    public PasswordErrorException(int status,String gripe){
        super(status,gripe);
    }
}
