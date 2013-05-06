package com.fever.liveppt.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.cache.Cache;
import play.libs.Json;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.AWSUtils;

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
			AmazonS3 s3 = AWSUtils.genTokyoS3();
			
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
	public ArrayNode getPptList(Long UserId) {
		// TODO Auto-generated method stub
		ArrayNode pptArrayNode = new ArrayNode(JsonNodeFactory.instance);;
		User user = User.find.byId(UserId);
		if (user!=null){
			List<Ppt> ppts = user.ppts;			 
			int index=0;
			for (Ppt ppt : ppts){
				pptArrayNode.add(ppt.toJsonNode());
				index++;
			}			
		}
		return pptArrayNode;
	}

}
