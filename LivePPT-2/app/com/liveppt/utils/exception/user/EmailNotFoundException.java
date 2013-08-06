package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黎伟杰
 */
public class EmailNotFoundException extends UserException {

    public EmailNotFoundException(){
        super(StatusCode.USER_EMAIL_NOT_FOUND);
    }

    public EmailNotFoundException(int status){
        super(status);
    }

    public EmailNotFoundException(int status,String gripe){
        super(status,gripe);
    }

}
