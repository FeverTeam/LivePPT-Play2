package com.fever.liveppt.service;

import com.fever.liveppt.exception.common.InternalErrorException;
import com.fever.liveppt.exception.ppt.PptNotConvertedException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.ppt.PptPageOutOfRangeException;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.exception.common.InvalidParamsException;
import org.codehaus.jackson.JsonNode;

import java.util.List;

/**
 * PPT服务
 *
 * @author 梁博文
 */
public interface PptService {

    public byte[] getPptPage(Long pptId, Long pageId) throws PptNotExistedException, PptNotConvertedException, PptPageOutOfRangeException, InternalErrorException;

    public void updatePptConvertedStatus(JsonNode messageJson);

    /**
     * 获取指定PPT信息
     *
     * @param pptId
     * @return
     */
    public JsonResult getPptInfo(Long pptId);

    /**
     * 获取用户PPT信息列表
     *
     * @param userId
     * @return JSON格式PPT信息列表
     */
    public JsonResult getPptList(Long userId);

    /**
     * 获取用户PPT信息列表
     * @param userEmail
     * @return JSON格式PPT信息列表
     */
    public List<Ppt> getPptList(String userEmail) throws InvalidParamsException;

    public Ppt getSinglePptInfo(long pptId);

    /*
    public byte[] getPptPageAsMid(Long pptId, Long pageId);

    public byte[] getPptPageAsBig(Long pptId, Long pageId);

    public byte[] getPptPageAsSmall(Long pptId, Long pageId);
    */
}