package com.liveppt.utils.models;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * description
 * author 黎伟杰
 */
public class UserJson extends ObjectNode{

    public static String KEY_ID = "userId";
    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_DISPLAY = "display";

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
        this.put(KEY_EMAIL,email);
        return this;
    }

    /**
     * 从UserJson取出email状态信息
     * @param
     * @return  email
     * last modified 黎伟杰
     */
    public String getEmail(){
        return this.get(KEY_EMAIL).asText();
    }

    /**
     * 为UserJson加入password信息
     * @param password
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putPassword(String password){
        this.put(KEY_PASSWORD,password);
        return this;
    }

    /**
     * 从UserJson取出password状态信息
     * @param
     * @return  password
     * last modified 黎伟杰
     */
    public String getPassword(){
        return this.get(KEY_PASSWORD).asText();
    }

    /**
     * 为UserJson加入display信息
     * @param display
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putDisplay(String display){
        this.put(KEY_DISPLAY,display);
        return this;
    }

    /**
     * 从UserJson取出display状态信息
     * @param
     * @return  display
     * last modified 黎伟杰
     */
    public String getDisplay(){
        return this.get(KEY_DISPLAY).asText();
    }

    /**
     * 为UserJson加入userId信息
     * @param id
     * @return  itself
     * last modified 黎伟杰
     */
    public UserJson putId(Long id){
        this.put(KEY_ID,id);
        return this;
    }

    /**
     * 从UserJson取出userId信息
     * @param
     * @return  id
     * last modified 黎伟杰
     */
    public Long getId(){
        return this.get(KEY_DISPLAY).asLong();
    }

}
