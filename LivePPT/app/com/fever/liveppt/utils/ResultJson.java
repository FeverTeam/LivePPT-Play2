package com.fever.liveppt.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fever.liveppt.exception.BasicException;
import play.libs.Json;

/**
 * Created with IntelliJ IDEA.
 * User: Zijing Lee
 * Date: 13-9-27
 * Time: 上午10:42
 * Description:  封装数据方向接口的自定义JSON格式，即接口返回数据的JSON格式
 */
public class ResultJson {
    public static final String KEY_DATA = "data";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_STATUS_CODE = "retcode";

    public ObjectNode objectNode;

    public ResultJson(Integer retCode, String message, JsonNode dataNode) {
        this.objectNode = Json.newObject();
        this.setStatusCode(retCode);
        this.setMessage(message);
        this.setData(dataNode);
    }


    public ResultJson(BasicException basicException) {
        this(basicException.getRetcode(), basicException.getMessage(), null);
    }

    public static ResultJson simpleSuccess() {
        return new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, null);
    }

    //getter and setter
    public String getMessage() {
        return this.objectNode.get(KEY_MESSAGE).asText();
    }

    public void setMessage(String message) {
        this.objectNode.put(KEY_MESSAGE, message);
    }

    public ObjectNode getData() {
        return (ObjectNode) this.objectNode.get(KEY_DATA);
    }

    public void setData(JsonNode data) {
        this.objectNode.put(KEY_DATA, data);
    }

    public Integer getStatusCode() {
        return this.objectNode.get(KEY_STATUS_CODE).asInt();
    }

    public void setStatusCode(Integer statusCode) {
        this.objectNode.put(KEY_STATUS_CODE, statusCode);
    }
}
