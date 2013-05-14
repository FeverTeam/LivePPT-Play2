package com.fever.liveppt.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 有关JSON的操作工具类
 * @author 梁博文
 *
 */
public class JsonResult extends ObjectNode {

	public final static String KEY_IS_SUCCESS = "isSuccess";
	public final static String KEY_DATA = "data";
	public final static String KEY_MESSAGE = "message";

	/**
	 * 用于封装JSON接口的返回结果
	 * @param isSuccess 是否执行成功
	 * @param message 附带信息
	 * @param dataNode 数据，以JsonNode类封装的JSON格式
	 * @return
	 */
	public static JsonResult genResultJson(Boolean isSuccess, String message, JsonNode dataNode) {
		return new JsonResult(isSuccess, message, dataNode);
	}
	
	/**
	 * 用于封装JSON接口的返回结果
	 * @param isSuccess 是否执行成功
	 * @param dataNode 数据，以JsonNode类封装的JSON格式
	 * @return
	 */
	public static JsonResult genResultJson(Boolean isSuccess, JsonNode dataNode) {
		return new JsonResult(isSuccess, dataNode);
	}
	
	//Constructors
	
	public JsonResult(Boolean isSuccess){
		this(isSuccess, "", null);
	}
	
	public JsonResult(Boolean isSuccess, String message){
		this(isSuccess, message, null);
	}
	
	public JsonResult(Boolean isSuccess, JsonNode dataNode){
		this(isSuccess, "", dataNode);
	}
	
	
	public JsonResult(Boolean isSuccess, String message, JsonNode dataNode){
		super(JsonNodeFactory.instance);
		this.setIsSuccess(isSuccess);
		this.setMessage(message);
		this.setData(dataNode);
	}
	
	//Getters and Setters
	
	public Boolean getIsSuccess(){
		return this.get(KEY_IS_SUCCESS).getValueAsBoolean();
	}
	
	public void setIsSuccess(boolean isSuccess){
		this.put(KEY_IS_SUCCESS, isSuccess);
	}
	
	public String getMessage(){
		return this.get(KEY_MESSAGE).getTextValue();
	}
	
	public void setMessage(String message){
		this.put(KEY_MESSAGE, message);
	}
	
	public ObjectNode getData(){
		return (ObjectNode) this.get(KEY_DATA);
	}
	
	public void setData(JsonNode data){
		this.put(KEY_DATA, data);
	}
	
	
}
