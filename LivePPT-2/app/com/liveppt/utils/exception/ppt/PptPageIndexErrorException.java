package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class PptPageIndexErrorException extends PptException {

    public PptPageIndexErrorException(){
        super(StatusCode.PPT_PAGE_INDEX_ERROR);
    }

    public PptPageIndexErrorException(int status){
        this(status,"PPT_PAGE_INDEX_ERROR");
    }

    public PptPageIndexErrorException(int status, String gripe){
        super(status,gripe);
    }

}
