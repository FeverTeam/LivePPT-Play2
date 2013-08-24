package com.liveppt.utils.models;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.util.Map;

/**
 * description
 * author 黎伟杰
 */
public class MeetingJson extends ObjectNode{


    public MeetingJson() {
        super(JsonNodeFactory.instance);
    }

    public MeetingJson setStringField(Map<String, String> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }

    public MeetingJson setJsonField(Map<String, JsonNode> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }


}