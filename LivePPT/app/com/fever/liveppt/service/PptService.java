package com.fever.liveppt.service;

import java.io.InputStream;

import org.codehaus.jackson.JsonNode;

/**
 * PPT服务
 * @author 梁博文
 *
 */
public interface PptService {

	public byte[] getPptPage(Long pptId, Long pageId);

	public void updatePptConvertedStatus(JsonNode messageJson);

}