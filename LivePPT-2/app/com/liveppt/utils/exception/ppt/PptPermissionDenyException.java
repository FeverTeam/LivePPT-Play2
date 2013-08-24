package com.liveppt.utils.exception.ppt;

import com.liveppt.utils.StatusCode;

/**
 * Date: 13-8-18
 * Time: 下午5:40
 *
 * @author 黎伟杰
 */
public class PptPermissionDenyException extends PptException {

    public PptPermissionDenyException(){
        super(StatusCode.PPT_PERMISSION_DENY);
    }

    public PptPermissionDenyException(int status){
        this(status,"PPT_PERMISSION_DENY");
    }

    public PptPermissionDenyException(int status, String gripe){
        super(status,gripe);
    }

}
