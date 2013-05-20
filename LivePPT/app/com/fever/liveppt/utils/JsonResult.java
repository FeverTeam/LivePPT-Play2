package com.fever.liveppt.utils;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 接口对外通用JSON格式封装类
 * @author 梁博文,黎伟杰
 *
 */
public class JsonResult extends ObjectNode {

	public final static String KEY_IS_SUCCESS = "isSuccess";
	public final static String KEY_DATA = "data";
	public final static String KEY_MESSAGE = "message";
	public final static String KEY_STATUS_CODE = "statusCode";
	
	//Constructors

	/**
	 * 
	 * @param isSuccess 是否执行成功
	 */
	public JsonResult(Boolean isSuccess){
		this(isSuccess, "0000", null,"");
	}
	
	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param statusCode 状态码
	 */
	public JsonResult(Boolean isSuccess, String statusCode){
		this(isSuccess, statusCode, null,"");
	}
	
	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param dataNode JsonNode类型的数据
	 */
	public JsonResult(Boolean isSuccess, JsonNode dataNode){
		this(isSuccess, "0000", dataNode,"");
	}
	

	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param statusCode 状态码
	 * @param dataNode JsonNode类型的数据
	 */
	public JsonResult(Boolean isSuccess, String statusCode, JsonNode dataNode){
		super(JsonNodeFactory.instance);
		this.setIsSuccess(isSuccess);
		this.setMessage(statusCode);
		this.setData(dataNode);
		this.setMessage("");
	}

	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param statusCode 状态码
	 * @param message 附加信息
	 */
	public JsonResult(Boolean isSuccess, String statusCode,String message){
		this(isSuccess, statusCode, null,message);
	}

	/**
	 * 
	 * @param isSuccess 是否执行成功
	 * @param statusCode 状态码
	 * @param dataNode JsonNode类型的数据
	 * @param message 附加信息
	 */
	public JsonResult(Boolean isSuccess, String statusCode, JsonNode dataNode,String message){
		super(JsonNodeFactory.instance);
		this.setIsSuccess(isSuccess);
		this.setMessage(statusCode);
		this.setData(dataNode);
		this.setMessage(message);
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
	
	public JsonResult setMessage(String message){
		this.put(KEY_MESSAGE, message);
		return this;
		
	}
	
	public ObjectNode getData(){
		return (ObjectNode) this.get(KEY_DATA);
	}
	
	public void setData(JsonNode data){
		this.put(KEY_DATA, data);
	}

	public String getStatusCode()
	{
		return this.get(KEY_STATUS_CODE).getTextValue();
	}

	public void setStatusCode(String statusCode)
	{
		this.put(KEY_STATUS_CODE,statusCode);
	}
	
	
}
