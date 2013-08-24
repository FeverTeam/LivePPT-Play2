package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.exception.LivePPTException;

/**
 * Date: 13-8-18
 * Time: 下午5:38
 *
 * @author 黎伟杰
 */
public class PptException extends LivePPTException{

    public PptException(){
        super();
    }

    public PptException(int status){
        super(status);
    }

    public PptException(int status, String gripe){
        super(status,gripe);
    }

}
