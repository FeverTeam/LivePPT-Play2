package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class PptFileErrorException extends PptException {

    public PptFileErrorException(){
        super(StatusCode.PPT_FILE_ERROR);
    }

    public PptFileErrorException(int status){
        this(status,"PPT_FILE_ERROR");
    }

    public PptFileErrorException(int status, String gripe){
        super(status,gripe);
    }

}
