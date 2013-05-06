package com.fever.liveppt.service;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

public interface PptService {

	public byte[] getPptPage(Long pptId, Long pageId);

	public void updatePptConvertedStatus(JsonNode messageJson);
	
	/**
	 * 获取用户PPT信息列表
	 * @param UserId
	 * @return JSON格式PPT信息列表
	 */
	public ArrayNode getPptList(Long UserId);

}