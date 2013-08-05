package com.liveppt.services.impl;

import com.liveppt.models.dao.UserAccess;
import com.liveppt.services.UserService;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.models.UserJson;

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
    public UserJson regist(Map<String, String[]> params) throws UserException {
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
    public UserJson login(Map<String, String[]> params) throws UserException {
        //TODO 错误检查抛出
        return UserAccess.login(params);
    }

    /**
     * 修改密码
     * @param params
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserJson updatePassword(Map<String, String[]> params) throws UserException {
    	return UserAccess.updatePassword(params);
    }

    /**
     * 修改用户名
     * @param params
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserJson updateDisplay(Map<String, String[]> params) throws UserException {
    	return UserAccess.updateDisplay(params);
    }
}
