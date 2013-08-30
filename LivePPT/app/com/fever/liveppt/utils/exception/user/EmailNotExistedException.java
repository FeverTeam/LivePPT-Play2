package com.fever.liveppt.utils.exception.user;

import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.exception.UserException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-29
 * Time: 下午11:43
 * To change this template use File | Settings | File Templates.
 */
public class EmailNotExistedException extends UserException {

    public EmailNotExistedException()
    {
        super(StatusCode.EMAIL_NOT_EXISTED,StatusCode.EMAIL_NOT_EXISTED_MESSAGE)  ;
    }

    public EmailNotExistedException(int retcode)
    {
        super(retcode);
    }

    public EmailNotExistedException(String message) {
        super(StatusCode.EMAIL_NOT_EXISTED,message);
    }

    public EmailNotExistedException(int retcode,String message){
        super(retcode,message);
    }


}
