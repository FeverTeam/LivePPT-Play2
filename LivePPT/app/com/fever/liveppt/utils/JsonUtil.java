package com.fever.liveppt.utils;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

/**
 * 有关JSON的操作工具类
 * @author 梁博文
 *
 */
public class JsonUtil {

	public final static String IS_SUCCESS = "isSuccess";
	public final static String DATA = "data";

	/**
	 * 用于封装JSON接口的返回结果
	 * @param isSuccess 是否执行成功
	 * @param data 数据，以ObjectNode类封装的JSON格式
	 * @return
	 */
	public static ObjectNode genResultJson(Boolean isSuccess, ObjectNode data) {
		ObjectNode json = Json.newObject();
		json.put(IS_SUCCESS, isSuccess.toString());
		json.put(DATA, data);
		return json;
	}

}
