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

    public ResultJson(Integer retCode, JsonNode dataNode, String message) {
        super(JsonNodeFactory.instance);
        this.setStatusCode(retCode);
        this.setData(dataNode);
        this.setMessage(message);
    }


    public ResultJson(CommonException commonException) {
        super(JsonNodeFactory.instance);
        this.setStatusCode(commonException.getRetcode());
        this.setMessage(commonException.getMessage());
        this.setData(null);
    }

    public ResultJson(UserException userException) {
        super(JsonNodeFactory.instance);
        this.setStatusCode(userException.getRetcode());
        this.setMessage(userException.getMessage());
        this.setData(null);
    }

    //getter and setter
    public String getMessage() {
        return this.get(KEY_MESSAGE).getTextValue();
    }

    public ResultJson setMessage(String message) {
        this.put(KEY_MESSAGE, message);
        return this;

    }

    public ObjectNode getData() {
        return (ObjectNode) this.get(KEY_DATA);
    }

    public void setData(JsonNode data) {
        this.put(KEY_DATA, data);
    }

    public Integer getStatusCode() {
        return this.get(KEY_STATUS_CODE).getValueAsInt();
    }

    public void setStatusCode(Integer statusCode) {
        this.put(KEY_STATUS_CODE, statusCode);
    }
}
