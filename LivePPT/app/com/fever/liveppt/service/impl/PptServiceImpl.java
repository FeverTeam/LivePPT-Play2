package com.fever.liveppt.service.impl;

import java.util.List;

import org.codehaus.jackson.JsonNode;

import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.service.PptService;

public class PptServiceImpl implements PptService {

	@Override
	public void updatePptConvertedStatus(JsonNode messageJson) {
		// TODO Auto-generated method stub
		Boolean isSuccess = messageJson.findPath("isSuccess").getBooleanValue();
		if (!isSuccess.equals(null) && isSuccess){
			String storeKey = messageJson.findPath("storeKey").getTextValue();
			Long pageCount = messageJson.findPath("pageCount").getLongValue();
			
			List<Ppt> pptList = Ppt.find.where().eq("storeKey", storeKey).findList();
			Ppt ppt = pptList.get(0);
			ppt.isConverted = true;
			ppt.pageCount = pageCount;
			ppt.save();
		}
	}

}
