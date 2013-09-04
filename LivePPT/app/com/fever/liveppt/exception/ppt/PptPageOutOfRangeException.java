package com.fever.liveppt.exception.ppt;

import com.fever.liveppt.utils.StatusCode;
import play.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-9-5
 * Time: 上午12:23
 * To change this template use File | Settings | File Templates.
 */
public class PptPageOutOfRangeException extends PptException {
    public PptPageOutOfRangeException() {
        super(StatusCode.PPT_PAGE_OUT_OF_RANGE, StatusCode.PPT_PAGE_OUT_OF_RANGE_MESSAGE);
    }
}
