package com.liveppt.services;

import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.models.PptJson;

import java.io.File;
import java.util.Map;

/**
 * Date: 13-8-18
 * Time: 下午4:11
 *
 * @author 黎伟杰
 */
public interface PptService {

    public PptJson uploadPpt(Map<String, String[]> params, File file) throws PptException;

}
