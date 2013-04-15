package com.fever.liveppt.service;

import java.io.File;

import org.codehaus.jackson.JsonNode;

public interface PptService {

	public File getPptPage(Long pptId, Long pageId);

	public void updatePptConvertedStatus(JsonNode messageJson);

}