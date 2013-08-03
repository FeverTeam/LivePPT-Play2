package com.liveppt.services.impl;

import com.liveppt.models.User;
import com.liveppt.models.dao.UserAccess;
import com.liveppt.services.UserService;
import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;



/**
 * 用户接口的实现
 * author 黎伟杰
 */
public class UserServiceImpl implements UserService{


    /**
     * 用户注册
     * @param params
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserJson regist(Map<String, String[]> params) {
        //TODO 错误检查抛出
        return UserAccess.create(params);
    }

    /**
     * 用户登陆
     * @param params
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserJson login(Map<String, String[]> params) {
        //TODO 错误检查抛出
        return UserAccess.login(params);
    }

    /*
     * update by Email
     */
    public UserReader updateByEmail(UserReader userReader, String newEmail){
    	return UserAccess.updateByEmail(userReader, newEmail);
    }
    /*
     * update by Email
     */
    public UserReader updateByPassword(UserReader userReader, String newPassword){
    	return UserAccess.updateByEmail(userReader, newPassword);
    }
    /*
     * update by Email
     */
    public UserReader updateByDisplayname(UserReader userReader, String newDisplayname){
    	return UserAccess.updateByEmail(userReader, newDisplayname);
    }
}
