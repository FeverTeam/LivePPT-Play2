package com.liveppt.utils.exception.params;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黎伟杰
 */
public class DisplayNotFoundException extends  ParamsException{

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
