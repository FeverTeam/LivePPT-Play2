package com.liveppt.utils.exception.params;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黄梓财
 */
public class UserExistedException extends ParamsException{

    public UserExistedException(){
        super(StatusCode.USER_EMAIL_EXISTED);
    }

    public UserExistedException(int status){
        super(status);
    }

    public UserExistedException(int status,String gripe){
        super(status,gripe);
    }

}
