package com.fever.liveppt.service;

import com.fever.liveppt.exception.common.InternalErrorException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.ppt.*;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import org.codehaus.jackson.JsonNode;

import java.io.File;
import java.util.List;

/**
 * @author
 * @version : v1.00
 * @Description : PPT操作接口 ，提供给controller层调用
 *
 */
public interface PptService {

    /**
     * 获取PPT指定页面的图像
     *
     * @param pptId
     * @param pageId
     * @return
     * @throws PptNotExistedException
     * @throws PptNotConvertedException
     * @throws PptPageOutOfRangeException
     * @throws InternalErrorException
     */
    public byte[] getPptPage(String userEmail,Long pptId, Long pageId) throws PptNotExistedException, PptNotConvertedException, PptPageOutOfRangeException, InternalErrorException, PptNotPermissionDenyException;

    /**
     * 更新PPT转换状态
     *
     * @param messageJson SNS发来的JSON
     */
    public void updatePptConvertedStatus(JsonNode messageJson);

    /**
     * 获取用户PPT信息列表
     *
     * @param userEmail
     * @return JSON格式PPT信息列表
     */
    public List<Ppt> getPptList(String userEmail) throws InvalidParamsException;

    /**
     * 根据指定ID获取PPT信息
     *
     * @param pptId
     * @return
     */
    public Ppt getPpt(long pptId);

    /**
     * 上传PPT到S3并发送消息到SQS
     *
     * @param user
     * @param file
     * @param title
     * @param filesize
     */
    public void uploadPptToS3(User user, File file, String title, long filesize) throws InternalErrorException;

    /**
     * 删除PPT及其相关的会议和参与关系
     *
     * @param user
     * @param pptId
     */
    public void deletePpt(User user, long pptId) throws InternalErrorException, PptNotSelfOwnException;


}