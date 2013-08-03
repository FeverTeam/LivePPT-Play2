package com.liveppt.utils.exception;

import java.io.IOException;

/**
 * description
 * author 黎伟杰
 */
public class LivePPTException extends IOException {

    int status;

    public LivePPTException(){}

    public LivePPTException(int status){
        this.status = status;
    }

    public LivePPTException(int status,String gripe){
        super(gripe);
        this.status = status;
    }

    public int getStatus(){
        return status;
    }

}
