package com.liveppt.utils.exception.user;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午4:45
 * 没有找到userId
 * @author 黎伟杰
 */
public class UserIdNotFoundException extends UserException{

    public UserIdNotFoundException(){
        super(StatusCode.USER_ID_NOT_FOUND);
    }

    public UserIdNotFoundException(int status){
        this(status,"USER_ID_NOT_FOUND");
    }

    public UserIdNotFoundException(int status,String gripe){
        super(status,gripe);
    }

}
