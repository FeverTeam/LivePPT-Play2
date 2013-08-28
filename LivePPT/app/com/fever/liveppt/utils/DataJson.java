package com.fever.liveppt.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Zijing Lee
 * Date: 13-8-27
 * Time: 上午10:42
 * To change this template use File | Settings | File Templates.
 */
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

public class DataJson extends ObjectNode{

    public DataJson() {
        super(JsonNodeFactory.instance);
    }

    public DataJson setStringField(Map<String, String> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }

    public DataJson setJsonField(Map<String, JsonNode> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }
}