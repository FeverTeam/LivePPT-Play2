package com.fever.liveppt.utils.exception.user;

import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.exception.UserException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-29
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
public class PasswordNotMatchException extends UserException {

    public PasswordNotMatchException()
    {
        super(StatusCode.PASSWORD_NOT_MATCH,StatusCode.PASSWORD_NOT_MATCH_MESSAGE);
    }

    public PasswordNotMatchException(int retcode)
    {
        super(retcode);
    }

    public PasswordNotMatchException(String message)
    {
        super(StatusCode.PASSWORD_NOT_MATCH,message);
    }

    public PasswordNotMatchException(int retcode ,String message)
    {
        super(retcode,message);
    }
}
