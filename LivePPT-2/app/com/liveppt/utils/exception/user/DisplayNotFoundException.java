package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黎伟杰
 */
public class DisplayNotFoundException extends UserException {

    public DisplayNotFoundException(){
        super(StatusCode.USER_DISPLAY_NOT_FOUND);
    }

    public DisplayNotFoundException(int status){
        super(status);
    }

    public DisplayNotFoundException(int status,String gripe){
        super(status,gripe);
    }
}
