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
     * @param params
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

    public Long id;

    public String email;

    public String password;

    public String newPassword;

    public String display;

    public String newDisplay;

    public Map<String, String[]> params;

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
     * @return
     * @throws EmailNotFoundException
     * last modified 黎伟杰
     */
    public UserReader setEmail() throws EmailNotFoundException {
        String email = params.get(UserJson.KEY_EMAIL)[0];
        if (email==null) throw  new EmailNotFoundException();
        this.email = email;
        return this;
    }

    /**
     * 通过params设置password
     * @return
     * @throws PasswordNotFoundException
     * last modified 黎伟杰
     */
    public UserReader setPassword() throws PasswordNotFoundException {
        String password = params.get(UserJson.KEY_PASSWORD)[0];
        if (password==null) throw  new PasswordNotFoundException();
        this.password = password;
        return this;
    }

    /**
     * 通过params设置newPassword
     * @return
     * @throws NewPasswordNotFoundException
     * last modified 黎伟杰
     */
    public UserReader setNewPassword() throws NewPasswordNotFoundException {
        String newPassword = params.get(UserJson.KEY_NEW_PASSWORD)[0];
        if (newPassword==null) throw  new NewPasswordNotFoundException();
        this.newPassword = newPassword;
        return this;
    }

    /**
     * 通过params设置display
     * @return
     * @throws DisplayNotFoundException
     * last modified 黎伟杰
     */
    public UserReader setDisplay() throws DisplayNotFoundException {
        String display = params.get(UserJson.KEY_DISPLAY)[0];
        if (display==null) throw  new DisplayNotFoundException();
        this.display = display;
        return this;
    }

    /**
     * 通过params设置newPisplay
     * @return
     * @throws NewDisplayNotFoundException
     * last modified 黎伟杰
     */
    public UserReader setNewDisplay() throws NewDisplayNotFoundException {
        String newDisplay = params.get(UserJson.KEY_NEW_PASSWORD)[0];
        if (newDisplay==null) throw  new NewDisplayNotFoundException();
        this.newDisplay = newDisplay;
        return this;
    }
}
