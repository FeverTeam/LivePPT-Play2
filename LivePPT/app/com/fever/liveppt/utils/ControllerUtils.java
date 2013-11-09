package com.fever.liveppt.utils;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-8-28
 * Time: 下午11:20
 * Description: controller层所用到的参数检查等方法
 */
public class ControllerUtils {

    /**
     * 检查字段是否存在
     *
     * @param params    参数键值对
     * @param fieldName 字段名称
     * @return
     */
    public static boolean isFieldNotNull(Map<String, String[]> params, String fieldName) {
        return !(params == null || fieldName == null || fieldName.length() == 0) && !(!params.containsKey(fieldName) || params.get(fieldName)[0].length() == 0);
    }
}
