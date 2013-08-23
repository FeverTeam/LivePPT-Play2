package com.liveppt.services.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.liveppt.models.Ppt;
import com.liveppt.models.dao.PptAccess;
import com.liveppt.services.PptService;
import com.liveppt.utils.AwsConnGenerator;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.exception.ppt.PptFileErrorException;
import com.liveppt.utils.models.PptJson;
import com.liveppt.utils.models.PptReader;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import play.Logger;
import play.cache.Cache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Date: 13-8-18
 * Time: 下午5:19
 *
 * @author 黎伟杰
 */
public class PptServiceImpl implements PptService{

    private final static String QUEUE_NAME = "LivePPT-pptId-Bus";

    @Override
    public PptReader uploadPpt(PptReader pptReader, File file) throws PptException {

        String title = file.getName();
        Long filesize = file.length();
        String title2 = null;
        try {
            title2 = new String(title.getBytes("gbk"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw  new PptFileErrorException();
        }

        // 存入AmazonS3
        AmazonS3 s3 = AwsConnGenerator.genTokyoS3();
        String storeKey = UUID.randomUUID().toString().replaceAll("-", "");
        s3.putObject("pptstore", storeKey, file);

        Logger.debug("StoreKey:" + storeKey);

        // 设置存入DB的ppt信息,设置filename,filesize,storekey
        pptReader.setFileName(title2).setFileSize(filesize).setStoreKey(storeKey);

        //添加ppt信息
        pptReader = PptAccess.create(pptReader);

        // 向SNS写入PPT的id，并告知win端进行转换
        AmazonSQS sqs = AwsConnGenerator.genTokyoSQS();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(
                QUEUE_NAME);
        String myQueueUrl = sqs.createQueue(createQueueRequest)
                .getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(myQueueUrl, storeKey));

        return pptReader;
    }

    @Override
    public void updatePptConvertedStatus(JsonNode messageJson) {

        Boolean isSuccess = messageJson.findPath("isSuccess").getBooleanValue();
        if (!isSuccess.equals(null) && isSuccess) {
            String storeKey = messageJson.findPath("storeKey").getTextValue();
            int pageCount = messageJson.findPath("pageCount").getIntValue();
            PptAccess.updatePptConvertedStatus(storeKey,true,pageCount);
        }
    }

    @Override
    public byte[] getPptPage(Long id,Long pptId, Long pageId) {

        byte[] imgBytes = null;
        String storeKey = PptAccess.ifReadByPptId(id,pptId);
        String pageKey = storeKey + "p" + pageId;
        // 若文件存在于Cache中，则直接返回
        imgBytes = (byte[]) Cache.get(pageKey);
        if (imgBytes != null) {
            return imgBytes;
        } else {
            // 组装S3获取信息
            AmazonS3 s3 = AwsConnGenerator.genTokyoS3();

            GetObjectRequest getObjectRequest = new GetObjectRequest(
                    "pptstore", pageKey);
            S3Object obj = s3.getObject(getObjectRequest);
            // 转换为bytes
            try {
                imgBytes = IOUtils.toByteArray((InputStream) obj
                        .getObjectContent());
                Cache.set(pageKey, imgBytes);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return imgBytes;
        }
    }

    public byte[] getPptPageFromMeeting(Long id, Long meetingId, Long pageId){

        byte[] imgBytes = null;
        String storeKey = PptAccess.ifReadByMeetingId(id,meetingId);
        String pageKey = storeKey + "p" + pageId;
        // 若文件存在于Cache中，则直接返回
        imgBytes = (byte[]) Cache.get(pageKey);
        if (imgBytes != null) {
            return imgBytes;
        } else {
            // 组装S3获取信息
            AmazonS3 s3 = AwsConnGenerator.genTokyoS3();

            GetObjectRequest getObjectRequest = new GetObjectRequest(
                    "pptstore", pageKey);
            S3Object obj = s3.getObject(getObjectRequest);
            // 转换为bytes
            try {
                imgBytes = IOUtils.toByteArray((InputStream) obj
                        .getObjectContent());
                Cache.set(pageKey, imgBytes);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return imgBytes;
        }
    }

    @Override
    public PptReader getPptInfo(PptReader pptReader) throws PptException {
        pptReader = PptAccess.getPptInfo(pptReader);
        return pptReader;
    }

    @Override
    public Set<PptReader> getPptListInfo(PptReader pptReader) throws PptException {
        Set<PptReader> pptReaders;
        pptReaders = PptAccess.getPptListInfo(pptReader);
        return null;
    }
}
