package com.fever.liveppt.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 接口对外通用JSON格式封装类
 * @author 梁博文
 *
 */
public class JsonResult extends ObjectNode {

	public final static String KEY_IS_SUCCESS = "isSuccess";
	public final static String KEY_DATA = "data";
	public final static String KEY_MESSAGE = "message";
	
	//Constructors

	/**
	 * 
	 * @param isSuccess 是否执行成功
	 */
	public JsonResult(Boolean isSuccess){
		this(isSuccess, "", null);
	}
	
	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param message 附加信息
	 */
	public JsonResult(Boolean isSuccess, String message){
		this(isSuccess, message, null);
	}
	
	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param dataNode JsonNode类型的数据
	 */
	public JsonResult(Boolean isSuccess, JsonNode dataNode){
		this(isSuccess, "", dataNode);
	}
	
	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param message 附加信息
	 * @param dataNode JsonNode类型的数据
	 */
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
