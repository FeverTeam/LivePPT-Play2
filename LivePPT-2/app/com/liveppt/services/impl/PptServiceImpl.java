package com.liveppt.services.impl;

import com.amazonaws.services.s3.AmazonS3;
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
import org.codehaus.jackson.JsonNode;
import play.Logger;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Date: 13-8-18
 * Time: 下午5:19
 *
 * @author 黎伟杰
 */
public class PptServiceImpl implements PptService{

    private final static String QUEUE_NAME = "LivePPT-pptId-Bus";

    @Override
    public PptJson uploadPpt(Map<String, String[]> params, File file) throws PptException {

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

        //设置filename
        params.put(PptJson.KEY_PPT_FILENAME,new String[]{title2});
        //设置filesize
        params.put(PptJson.KEY_PPT_FILESIZE,new String[]{String.valueOf(filesize)});
        //设置storekey
        params.put(PptJson.KEY_PPT_STOREKEY,new String[]{storeKey});

        //添加ppt信息
        PptJson pptJson = PptAccess.create(params);

        // 向SNS写入PPT的id，并告知win端进行转换
        AmazonSQS sqs = AwsConnGenerator.genTokyoSQS();
        CreateQueueRequest createQueueRequest = new CreateQueueRequest(
                QUEUE_NAME);
        String myQueueUrl = sqs.createQueue(createQueueRequest)
                .getQueueUrl();
        sqs.sendMessage(new SendMessageRequest(myQueueUrl, storeKey));

        return pptJson;
    }

    @Override
    public void updatePptConvertedStatus(JsonNode messageJson) {

        Boolean isSuccess = messageJson.findPath("isSuccess").getBooleanValue();
        if (!isSuccess.equals(null) && isSuccess) {
            String storeKey = messageJson.findPath("storeKey").getTextValue();
            int pageCount = messageJson.findPath("pageCount").getIntValue();

            List<Ppt> pptList = Ppt.find.where().eq("storeKey", storeKey)
                    .findList();
            Ppt ppt = pptList.get(0);
            PptAccess.updatePptConvertedStatus(storeKey,true,pageCount);
        }
    }

}
