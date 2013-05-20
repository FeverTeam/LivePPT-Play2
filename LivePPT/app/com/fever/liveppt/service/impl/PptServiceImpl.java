package com.fever.liveppt.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;

import play.cache.Cache;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.AwsConnGenerator;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;

public class PptServiceImpl implements PptService {

	@Override
	public byte[] getPptPage(Long pptId, Long pageId) {
		// TODO Auto-generated method stub
		byte[] imgBytes = null;
		Ppt ppt = Ppt.find.where().eq("id", pptId).findUnique();
		String storeKey = ppt.storeKey;
		String pageKey = storeKey + "p" + pageId;
		imgBytes = (byte[]) Cache.get(pageKey);
		if (imgBytes!=null){
			return imgBytes;			
		} else {
			AmazonS3 s3 = AwsConnGenerator.genTokyoS3();
			
			GetObjectRequest getObjectRequest = new GetObjectRequest("pptstore",
					pageKey);
			S3Object obj = s3.getObject(getObjectRequest);
			
			try {
				 imgBytes = IOUtils.toByteArray((InputStream) obj.getObjectContent());
				Cache.set(pageKey, imgBytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return imgBytes;
		}
	}

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
		JsonResult resultJson ;
		// TODO Auto-generated method stub
		ArrayNode pptArrayNode = new ArrayNode(JsonNodeFactory.instance);
		User user = User.find.byId(UserId);
		if (user!=null){
			List<Ppt> ppts = user.ppts;
			if (ppts.size()==0)
			{
				resultJson = new JsonResult(false, StatusCode.PPT_LIST_NULL);
			}else 
			{
				for (Ppt ppt : ppts){
					pptArrayNode.add(ppt.toJsonNode());
				}			
				resultJson = new JsonResult(true, pptArrayNode);
			}
			
		}else
		{
			resultJson = new JsonResult(false, StatusCode.USER_NOT_EXISTED,"用户ID不存在");
		}
		return resultJson;
	}

	@Override
	public JsonResult getPptInfo(Long pptId) {
		// TODO Auto-generated method stub
		Ppt ppt = Ppt.find.byId(pptId);
		if (ppt==null){
			return new JsonResult(false,StatusCode.PPT_NOT_EXISTED ,"不存在该PPT");
		} else {
			return new JsonResult(true, ppt.toJsonNode());
		}
	}

}
