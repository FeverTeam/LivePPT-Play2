package com.fever.liveppt.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fever.liveppt.exception.common.InternalErrorException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.ppt.*;
import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.aws.AwsHelper;
import org.apache.commons.io.IOUtils;
import play.Logger;
import play.cache.Cache;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author
 * @version : v1.00
 * @Description : PPT操作接口实现 ，提供给service层调用
 */
public class PptServiceImpl implements PptService {


    @Override
    public byte[] getPptPage(String userEmail, Long pptId, Long pageId) throws PptNotExistedException, PptNotConvertedException, PptPageOutOfRangeException, InternalErrorException, PptNotPermissionDenyException {
        boolean ifPermission = false;
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null) {
            throw new PptNotExistedException();
        }

        User user = User.find.where().eq("email", userEmail).findUnique();

        for (Ppt userPpt : user.ppts) {
            if (pptId.equals(userPpt.id)) {
                ifPermission = true;
                break;
            }
        }
        if (!ifPermission) {
            for (Attender attender : user.attendents) {
                if (pptId.equals(attender.meeting.ppt.id)) {
                    ifPermission = true;
                    break;
                }

            }
        }

        if (!ifPermission) {
            throw new PptNotPermissionDenyException();
        }

        //检查是否已转换
        if (!ppt.isConverted) {
            throw new PptNotConvertedException();
        }

        //检查页数是否超出范围
        if (pageId < 0 || pageId > ppt.pagecount) {
            throw new PptPageOutOfRangeException();
        }

        try {
            byte[] imgBytes = null;
            String storeKey = ppt.storeKey;
            String pageKey = storeKey + "p" + pageId;
            // 若文件存在于Cache中，则直接返回
            imgBytes = (byte[]) Cache.get(pageKey);
            if (imgBytes != null && imgBytes.length > 0) {
                return imgBytes;
            } else {
                // 组装S3获取信息并获取页面图片
                AmazonS3 s3 = AwsHelper.genTokyoS3();
                GetObjectRequest getObjectRequest = new GetObjectRequest(AwsHelper.STORE_NAME, pageKey);
                S3Object obj = s3.getObject(getObjectRequest);

                // 转换为bytes
                imgBytes = IOUtils.toByteArray((InputStream) obj.getObjectContent());
                Cache.set(pageKey, imgBytes);

                return imgBytes;
            }
        } catch (Exception e) {
            throw new InternalErrorException();
        }

    }

    @Override
    public void updatePptConvertedStatus(JsonNode messageJson) {
        boolean isSuccess = messageJson.findPath("isSuccess").booleanValue();
        if ( !isSuccess) {
            Logger.info("isSuccess : false");
        }
         else
        {
                String storeKey = messageJson.findPath("storeKey").textValue();
                int pageCount = messageJson.findPath("pageCount").intValue();

                Logger.info("storeKey"+storeKey+" pageCount"+pageCount);

                List<Ppt> pptList = Ppt.find.where().eq("storeKey", storeKey).findList();
                Ppt ppt = pptList.get(0);
                ppt.isConverted = true;
                ppt.pagecount = pageCount;
                ppt.save();

        }
    }

    @Override
    public List<Ppt> getPptList(String userEmail) throws InvalidParamsException {
        if (userEmail == null || userEmail.length() <= 0) {
            throw new InvalidParamsException();
        }

        //获取用户PPT列表
        User user = User.find.where().eq("email", userEmail).findUnique();
        if (user != null) {
            return user.ppts;
        } else {
            return new LinkedList<>();
        }
    }

    @Override
    public Ppt getPpt(long pptId) {
        if (pptId < 0) {
            return null;
        }
        return Ppt.find.byId(pptId);
    }

    @Override
    public void uploadPptToS3(User user, File file, String title, long filesize) throws InternalErrorException {
        try {
            // 存入AmazonS3
            AmazonS3 s3 = AwsHelper.genTokyoS3();
            String storeKey = AwsHelper.genRandomStoreKey();
            s3.putObject(AwsHelper.STORE_NAME, storeKey, file);

            // 存入文件与用户的所有权关系
            Ppt newPpt = new Ppt(user.id, title, new Date(), storeKey, filesize);
            newPpt.save();

            Logger.debug("StoreKey:" + storeKey);

            // 向SNS写入PPT的id，并告知win端进行转换
            AmazonSQS sqs = AwsHelper.genTokyoSQS();
            CreateQueueRequest createQueueRequest = new CreateQueueRequest(AwsHelper.QUEUE_NAME);
            String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
            sqs.sendMessage(new SendMessageRequest(myQueueUrl, storeKey));

        } catch (Exception e) {
            Logger.error("error",e);
            throw new InternalErrorException();
        }

    }

    @Override
    public void deletePpt(User user, long pptId) throws InternalErrorException, PptNotSelfOwnException {
        if (user == null) {
            return;
        }

        //查找指定的PPT是否属于用户
        Ppt targetPpt = null;
        for (Ppt ppt : user.ppts) {
            if (pptId == ppt.id) {
                targetPpt = ppt;
            }
        }
        if (targetPpt == null) {
            //PPT不属于用户，抛出PptNotSelfOwnException
            throw new PptNotSelfOwnException();
        }

        try {
            //删除使用该PPT的Meeting
            for (Meeting meeting : user.myFoundedMeeting) {
                if (meeting.ppt.id.equals(targetPpt.id)) {
                    //删除该meeting的参与关系
                    for (Attender attender : meeting.attenders) {
                        attender.delete();
                    }

                    //删除meeting
                    meeting.delete();
                }
            }

            //删除PPT
            targetPpt.delete();

        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalErrorException();
        }
    }

}
