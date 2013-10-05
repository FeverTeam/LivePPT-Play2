package com.fever.liveppt.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Zijing Lee
 * Date: 13-8-27
 * Time: 上午10:42
 * Description: 封装Json格式的数据
 */

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;

import java.util.Map;

public class DataJson {

    public ObjectNode objectNode;

    public DataJson() {
        this.objectNode = Json.newObject();
    }

    public DataJson(Map<String, String> keyValue) {
        this();
        this.setStringField(keyValue);
    }

    public DataJson setStringField(Map<String, String> keyValue) {
        if (keyValue == null || keyValue.size() == 0) {
            return this;
        }

        for (String key : keyValue.keySet()) {
            this.objectNode.put(key, keyValue.get(key));
        }

        return this;
    }

    public DataJson setJsonField(Map<String, JsonNode> keyValue) {
        if (keyValue == null || keyValue.size() == 0) {
            return this;
        }

        for (String key : keyValue.keySet()) {
            this.objectNode.put(key, keyValue.get(key));
        }

        return this;
    }
}