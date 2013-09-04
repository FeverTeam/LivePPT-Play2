package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.exception.BasicException;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-4
 * Time: 下午3:24
 * To change this template use File | Settings | File Templates.
 */
public class PptException extends BasicException {

    public PptException(int retcode, String message) {
        super(retcode, message);
    }
}
