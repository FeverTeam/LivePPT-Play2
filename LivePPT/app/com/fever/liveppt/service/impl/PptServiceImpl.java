package com.fever.liveppt.service.impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fever.liveppt.exception.common.InternalErrorException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.ppt.PptNotConvertedException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.ppt.PptPageOutOfRangeException;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.AwsConnGenerator;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.Logger;
import play.cache.Cache;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class PptServiceImpl implements PptService {

    @Override
    public byte[] getPptPage(Long pptId, Long pageId) throws PptNotExistedException, PptNotConvertedException, PptPageOutOfRangeException, InternalErrorException {
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null) {
            throw new PptNotExistedException();
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
            if (imgBytes != null) {
                return imgBytes;
            } else {
                // 组装S3获取信息并获取页面图片
                AmazonS3 s3 = AwsConnGenerator.genTokyoS3();
                GetObjectRequest getObjectRequest = new GetObjectRequest(
                        "pptstore", pageKey);
                S3Object obj = s3.getObject(getObjectRequest);

                // 转换为bytes
                imgBytes = IOUtils.toByteArray((InputStream) obj
                        .getObjectContent());
                Cache.set(pageKey, imgBytes);

                return imgBytes;
            }
        } catch (Exception e) {
            throw new InternalErrorException();
        }

    }

    /*
    public byte[] getPptPageAsSmall(Long pptId, Long pageId) {
        InputStream input = new ByteArrayInputStream(getPptPageImage(pptId, pageId));
        try {
            BufferedImage img = ImageIO.read(input);
            img = Scalr.resize(img, Scalr.Method.AUTOMATIC,
                    Scalr.Mode.FIT_TO_WIDTH, 300, 100, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bStream);
            return bStream.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getPptPageAsMid(Long pptId, Long pageId) {
        InputStream input = new ByteArrayInputStream(getPptPageImage(pptId, pageId));
        try {
            BufferedImage img = ImageIO.read(input);
            img = Scalr.resize(img, Scalr.Method.AUTOMATIC,
                    Scalr.Mode.FIT_TO_WIDTH, 500, 100, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bStream);
            return bStream.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public byte[] getPptPageAsBig(Long pptId, Long pageId) {
        InputStream input = new ByteArrayInputStream(getPptPageImage(pptId, pageId));
        try {
            BufferedImage img = ImageIO.read(input);
            img = Scalr.resize(img, Scalr.Method.AUTOMATIC,
                    Scalr.Mode.FIT_TO_WIDTH, 800, 100, Scalr.OP_ANTIALIAS);
            ByteArrayOutputStream bStream = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bStream);
            return bStream.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
    */

    @Override
    public void updatePptConvertedStatus(JsonNode messageJson) {
        // TODO Auto-generated method stub
        Boolean isSuccess = messageJson.findPath("isSuccess").getBooleanValue();
        if (!isSuccess.equals(null) && isSuccess) {
            String storeKey = messageJson.findPath("storeKey").getTextValue();
            int pageCount = messageJson.findPath("pageCount").getIntValue();

            List<Ppt> pptList = Ppt.find.where().eq("storeKey", storeKey)
                    .findList();
            Ppt ppt = pptList.get(0);
            ppt.isConverted = true;
            ppt.pagecount = pageCount;
            ppt.save();
        }
    }

    @Override
    public JsonResult getPptList(Long UserId) {
        JsonResult resultJson;
        ArrayNode pptArrayNode = new ArrayNode(JsonNodeFactory.instance);
        User user = User.find.byId(UserId);
        if (user != null) {
            List<Ppt> ppts = user.ppts;
            if (ppts.size() == 0) {
                resultJson = new JsonResult(false, StatusCode.PPT_LIST_NULL);
            } else {
                for (Ppt ppt : ppts) {
                    pptArrayNode.add(ppt.toJsonNode());
                }
                resultJson = new JsonResult(true, pptArrayNode);
            }

        } else {
            resultJson = new JsonResult(false, StatusCode.USER_NOT_EXISTED,
                    "用户ID不存在");
        }
        return resultJson;
    }

    @Override
    public JsonResult getPptInfo(Long pptId) {
        // TODO Auto-generated method stub
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null) {
            return new JsonResult(false, StatusCode.PPT_NOT_EXISTED, "不存在该PPT");
        } else {
            return new JsonResult(true, ppt.toJsonNode());
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
            return new LinkedList<Ppt>();
        }
    }

    @Override
    public Ppt getSinglePptInfo(long pptId) {
        if (pptId < 0) {
            return null;
        }
        Ppt ppt = Ppt.find.byId(pptId);
        return ppt;
    }

}
