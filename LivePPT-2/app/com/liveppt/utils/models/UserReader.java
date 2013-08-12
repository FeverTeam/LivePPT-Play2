package com.liveppt.utils.models;

import com.liveppt.utils.exception.user.EmailNotFoundException;
import com.liveppt.utils.exception.user.PasswordNotFoundException;
import com.liveppt.utils.exception.user.UserException;

import java.util.Map;

/**
 * 用户类载体
 * author 黎伟杰
 */
public class UserReader {

    public static String KEY_EMAIL = "email";
    public static String KEY_PASSWORD = "password";
    public static String KEY_NEW_PASSWORD = "newPassword";
    public static String KEY_DISPLAY = "display";
    public static String KEY_NEW_DISPLAY = "newDisplay";

    //constructors

    /**
     * 产生UserReader类
     * @param email 传入email
     * @param password 传入password
     * @return
     * last modified 黎伟杰
     */
    public UserReader(String email,String password){
        this.email = email;
        this.password = password;
    }

    //TODO 编写field的setter和getter ,和对应的抛出异常，构造函数中调用方法

    /**
     * 产生UserReader类
     * @param params
     * @return
     * last modified 黎伟杰
     */
    public UserReader(Map<String, String[]> params) throws UserException {
        System.out.println("genUserR");
        if (params.get(KEY_EMAIL)[0]==null) throw  new EmailNotFoundException();
        this.email = params.get(KEY_EMAIL)[0];
        if (params.get(KEY_PASSWORD)[0]==null) throw  new PasswordNotFoundException();
        this.password = params.get(KEY_PASSWORD)[0];
    }

    public Long id;

    public String email;

    public String password;

    public String newPassword;

    public String display;

    public String newDisplay;

    public int status;


}
