package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黄梓财
 */
public class NewDisplayNotFoundException extends UserException {

    public NewDisplayNotFoundException(){
        super(StatusCode.USER_NEW_DISPLAY_NOT_FOUND);
    }

    public NewDisplayNotFoundException(int status){
        super(status);
    }

    public NewDisplayNotFoundException(int status,String gripe){
        super(status,gripe);
    }
}