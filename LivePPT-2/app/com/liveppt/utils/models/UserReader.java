package com.liveppt.utils.models;

import com.liveppt.utils.exception.user.*;

import java.util.Map;

/**
 * 用户类载体
 * author 黎伟杰
 */
public class UserReader {


    //constructors

    /**
     * 产生UserReader类
     * @param
     * @return
     * last modified 黎伟杰
     */
    public UserReader() {
        id =null;
        email = null;
        password = null;
        newPassword = null;
        display = null;
        newDisplay = null;
    }

    private Long id;

    private String email;

    private String password;

    private String newPassword;

    private String display;

    private String newDisplay;

    /**
     * 设置Id
     * @return itself
     * last modified 黎伟杰
     */
    public UserReader setId(Long id)  {
        this.id = id;
        return this;
    }

    /**
     * 得到id
     * @return id
     * @throws UserIdNotFoundException
     * last modified 黎伟杰
     */
    public Long getId() throws UserIdNotFoundException {
        if (id==null) throw new UserIdNotFoundException();
        return id;
    }

    /**
     * 设置email
     * @return 本身
     * last modified 黎伟杰
     */
    public UserReader setEmail(String email)  {
        this.email = email;
        return this;
    }

    /**
     * 得到email
     * @return email
     * @throws EmailNotFoundException
     * last modified 黎伟杰
     */
    public String getEmail() throws EmailNotFoundException {
        if (email==null) throw  new EmailNotFoundException();
        return email;
    }

    /**
     * 设置password
     * @return itself
     * last modified 黎伟杰
     */
    public UserReader setPassword(String password)  {
        this.password = password;
        return this;
    }

    /**
     * 获得password
     * @return password
     * @throws PasswordNotFoundException
     * last modified 黎伟杰
     */
    public String getPassword() throws PasswordNotFoundException {
        if (this.password==null) throw new PasswordNotFoundException();
        return password;
    }

    /**
     * 设置newPassword
     * @return
     * last modified 黎伟杰
     */
    public UserReader setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    /**
     * 获得newPassword
     * @return
     * @throws NewPasswordNotFoundException
     * last modified 黎伟杰
     */
    public String getNewPassword() throws NewPasswordNotFoundException {
        if (this.newPassword==null) throw new NewPasswordNotFoundException();
        return this.newPassword ;

    }

    /**
     * 设置display
     * @return
     * last modified 黎伟杰
     */
    public UserReader setDisplay(String display)  {
        this.display = display;
        return this;
    }

    /**
     * 获得display
     * @return
     * @throws DisplayNotFoundException
     * last modified 黎伟杰
     */
    public String getDisplay() throws DisplayNotFoundException  {
        if (this.display==null) throw new DisplayNotFoundException();
        return this.display ;
    }

    /**
     * 设置newPisplay
     * last modified 黎伟杰
     */
    public UserReader setNewDisplay(String newDisplay) {
        this.newDisplay = newDisplay;
        return this;
    }

    /**
     * 获得newPisplay
     * @return
     * @throws NewDisplayNotFoundException
     * last modified 黎伟杰
     */
    public String getNewDisplay() throws NewDisplayNotFoundException {
        if (newDisplay==null) throw  new NewDisplayNotFoundException();
        return  this.newDisplay;
    }
}
