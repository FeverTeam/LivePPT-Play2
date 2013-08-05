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
    static String  MESSAGE = "message";

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

    public UserJson(int status){
        super(JsonNodeFactory.instance);
        putStatus(status);
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
     * 从UserJson取出email状态信息
     * @param
     * @return  itself
     * last modified 黎伟杰
     */
    public String getEmail(){
        return this.get(EMAIL).asText();
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
     * 从UserJson取出password状态信息
     * @param
     * @return  itself
     * last modified 黎伟杰
     */
    public String getPassword(){
        return this.get(PASS).asText();
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
     * 从UserJson取出display状态信息
     * @param
     * @return  itself
     * last modified 黎伟杰
     */
    public String getDisplay(){
        return this.get(DISPLAY).asText();
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

    /**
     * 从UserJson取出status状态信息
     * @param
     * @return  itself
     * last modified 黎伟杰
     */
    public String getStatus(){
        return this.get(STATUS).asText();
    }

}
