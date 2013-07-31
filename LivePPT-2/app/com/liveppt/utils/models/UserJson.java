package com.liveppt.utils.models;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * description
 * author 黎伟杰
 */
public class UserJson extends ObjectNode{

    static String  EMAIL = "email";
    static String  PASS = "password";
    static String  DISPLAY = "display";
    static String  STATUS = "status";

    /**
     * 初始化产生UserJson
     * @param email
     * @param password
     * @param display
     * last modified 黎伟杰
     */
    public UserJson(String email, String password, String display){
        super(JsonNodeFactory.instance);
        putEmail(email);
        putPassword(password);
        putDisplay(display);
    }

    /**
     * 为UserJson加入email信息
     * @param email
     * @return itselt
     * last modified 黎伟杰
     */
    public UserJson putEmail(String email){
        this.put(EMAIL,email);
        return this;
    }

    /**
     * 为UserJson加入password信息
     * @param password
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putPassword(String password){
        this.put(PASS,password);
        return this;
    }

    /**
     * 为UserJson加入display信息
     * @param display
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putDisplay(String display){
        this.put(DISPLAY,display);
        return this;
    }

    /**
     * 为UserJson加入status状态信息
     * @param status
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putStatus(int status){
        this.put(STATUS,status);
        return this;
    }

}
