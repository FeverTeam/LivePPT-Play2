package com.liveppt.services.impl;

import com.liveppt.models.dao.PptAccess;
import com.liveppt.services.PptService;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.models.PptJson;

import java.io.File;
import java.util.Map;

/**
 * Date: 13-8-18
 * Time: 下午5:19
 *
 * @author 黎伟杰
 */
public class PptServiceImpl implements PptService{

    @Override
    public PptJson uploadPpt(Map<String, String[]> params, File file) throws PptException {
        //添加ppt信息
        PptJson pptJson = PptAccess.create(params);

        //TODO 上传文件

        return pptJson;
    }

}
