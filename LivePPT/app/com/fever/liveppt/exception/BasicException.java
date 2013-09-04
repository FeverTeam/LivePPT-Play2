package com.fever.liveppt.exception;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-4
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class BasicException extends Exception {
    private int retcode;
    private String message;

    public BasicException(int retcode, String message) {
        super();
        this.setRetcode(retcode);
        this.setMessage(message);
    }

    //Getters and Setters
    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
