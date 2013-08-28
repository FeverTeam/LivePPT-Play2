package com.fever.liveppt.utils.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-28
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
public class UserException extends  Exception {
    int retcode;
    String message;

    public UserException(){};

    public UserException(int retcode)
    {
        this.retcode = retcode;
    }

    public UserException(int retcode,String message)
    {
        super(message);
        this.retcode = retcode;
        this.message = message;
    }

    public int getRetcode(){
        return retcode;
    }

    public String getMessage(){
        return message;
    }
}
