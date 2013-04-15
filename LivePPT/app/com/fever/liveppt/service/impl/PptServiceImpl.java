package com.fever.liveppt.service.impl;

import java.io.File;
import java.util.List;

import org.codehaus.jackson.JsonNode;

import play.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.AWSUtils;

public class PptServiceImpl implements PptService {

	@Override
	public File getPptPage(Long pptId, Long pageId) {
		// TODO Auto-generated method stub
		AmazonS3 s3 = AWSUtils.genTokyoS3();
		Ppt ppt = Ppt.find.where().eq("id", pptId).findUnique();
		String storeKey = ppt.storeKey;
		String pageKey = storeKey + "p" + pageId;
		GetObjectRequest getObjectRequest = new GetObjectRequest("pptstore", pageKey);
		File destFile = new File("c:\\temp\\"+ppt.title+"-"+pageId+".jpg");
		s3.getObject(getObjectRequest, destFile);
		Logger.info("destName:"+destFile.getName());
		return destFile;
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

}
