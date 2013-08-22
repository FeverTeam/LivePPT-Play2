package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class PptIdErrorException extends PptException {

    public PptIdErrorException(){
        super(StatusCode.PPT_ID_ERROR);
    }

    public PptIdErrorException(int status){
        this(status,"PPT_ID_ERROR");
    }

    public PptIdErrorException(int status, String gripe){
        super(status,gripe);
    }

}
