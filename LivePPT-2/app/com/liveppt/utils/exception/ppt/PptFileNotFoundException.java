package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class PptFileNotFoundException extends PptException {

    public PptFileNotFoundException(){
        super(StatusCode.PPT_FILE_NOT_FOUND);
    }

    public PptFileNotFoundException(int status){
        this(status,"PPT_FILE_NOT_FOUND");
    }

    public PptFileNotFoundException(int status, String gripe){
        super(status,gripe);
    }

}
