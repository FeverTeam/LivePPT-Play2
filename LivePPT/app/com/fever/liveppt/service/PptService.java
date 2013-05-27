package com.fever.liveppt.service;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import play.mvc.Content;

import com.fever.liveppt.utils.JsonResult;

/**
 * PPT服务
 * @author 梁博文
 *
 */
public interface PptService {

	public byte[] getPptPage(Long pptId, Long pageId);

	public void updatePptConvertedStatus(JsonNode messageJson);
	
	/**
	 * 获取指定PPT信息
	 * @param pptId
	 * @return
	 */
	public JsonResult getPptInfo(Long pptId);
	
	/**
	 * 获取用户PPT信息列表
	 * @param userId
	 * @return JSON格式PPT信息列表
	 */
	public JsonResult getPptList(Long userId);

	public byte[] getPptPageAsMid(Long pptId, Long pageId);

}