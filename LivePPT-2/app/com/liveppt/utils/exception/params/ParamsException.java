package com.liveppt.utils.exception.params;

import com.liveppt.utils.exception.LivePPTException;

/**
 * description
 * author 黎伟杰
 */
public class ParamsException extends LivePPTException {

    public ParamsException(){
        super();
    }

    public ParamsException(int status){
        super(status);
    }

    public ParamsException(int status,String gripe){
        super(status,gripe);
    }
}
