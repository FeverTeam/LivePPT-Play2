package com.fever.liveppt.utils;

import com.fever.liveppt.utils.exception.CommonException;
import com.fever.liveppt.utils.exception.UserException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-28
 * Time: 上午12:01
 * To change this template use File | Settings | File Templates.
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


    public ResultJson(CommonException commonException) {
        this(commonException.getRetcode(), commonException.getMessage(), null);
    }

    public ResultJson(UserException userException) {
        this(userException.getRetcode(), userException.getMessage(), null);
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
