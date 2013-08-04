package com.liveppt.utils.exception.params;

import com.liveppt.utils.StatusCode;

/**
 * Email不存在于数据库中
 * author 黎伟杰
 */
public class EmailNotExistedException extends ParamsException{

    public EmailNotExistedException(){
        super(StatusCode.USER_NOT_EXISTED);
    }

    public EmailNotExistedException(int status){
        super(status);
    }

    public EmailNotExistedException(int status, String gripe){
        super(status,gripe);
    }

}
