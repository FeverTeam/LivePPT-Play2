package com.fever.liveppt.utils;

import com.fever.liveppt.exception.BasicException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 封装数据方向接口的自定义JSON格式
 */
public class ResultJson extends ObjectNode {
    public final static String KEY_DATA = "data";
    public final static String KEY_MESSAGE = "message";
    public final static String KEY_STATUS_CODE = "retcode";

    public ResultJson(Integer retCode, String message, JsonNode dataNode) {
        super(JsonNodeFactory.instance);
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
        return this.get(KEY_MESSAGE).getTextValue();
    }

    public void setMessage(String message) {
        this.put(KEY_MESSAGE, message);
    }

    public ObjectNode getData() {
        return (ObjectNode) this.get(KEY_DATA);
    }

    public void setData(JsonNode data) {
        this.put(KEY_DATA, data);
    }

    public Integer getStatusCode() {
        return this.get(KEY_STATUS_CODE).getIntValue();
    }

    public void setStatusCode(Integer statusCode) {
        this.put(KEY_STATUS_CODE, statusCode);
    }
}
