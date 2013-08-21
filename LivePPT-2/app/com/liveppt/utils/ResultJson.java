package com.liveppt.utils;

import com.liveppt.utils.exception.LivePPTException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * 信息返回类
 * author 黎伟杰
 */
public class ResultJson extends ObjectNode {

    public final static String KEY_DATA = "data";
    public final static String KEY_MESSAGE = "message";
    public final static String KEY_STATUS_CODE = "statusCode";

    public ResultJson(LivePPTException livepptException){
        super(JsonNodeFactory.instance);
        this.putStatus(livepptException.getStatus());
        this.putMessage(livepptException.getMessage());
    }

    public ResultJson(JsonNode jsonNode){
        super(JsonNodeFactory.instance);
        this.putStatus(StatusCode.ALL_IS_OK);
        this.putMessage("All is ok!");
        this.putData(jsonNode);
    }

    public ResultJson(byte[] bytes){
        super(JsonNodeFactory.instance);
        this.putStatus(StatusCode.ALL_IS_OK);
        this.putMessage("All is ok!");
        this.putData(bytes);
    }

    public String getMessage() {
        return this.get(KEY_MESSAGE).getTextValue();
    }

    public ResultJson putMessage(String message) {
        this.put(KEY_MESSAGE, message);
        return this;
    }

    public ObjectNode getData() {
        return (ObjectNode) this.get(KEY_DATA);
    }

    public void putData(JsonNode data) {
        this.put(KEY_DATA, data);
    }

    public void putData(byte[] bytes) {
        this.put(KEY_DATA, bytes);
    }

    public long getStatusCode() {
        return this.get(KEY_STATUS_CODE).asLong();
    }

    public void putStatus(long statusCode) {
        this.put(KEY_STATUS_CODE, statusCode);
    }


}
