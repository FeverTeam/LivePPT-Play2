package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * description
 * author 黎伟杰
 */
public class UserNoLoginException extends UserException{

    public UserNoLoginException(){
        super(StatusCode.USER_NO_LOGIN);
    }

    public UserNoLoginException(int status){
        super(status);
    }

    public UserNoLoginException(int status,String gripe){
        super(status,gripe);
    }

}
