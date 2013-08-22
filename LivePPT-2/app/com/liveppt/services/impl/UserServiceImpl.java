package com.liveppt.services.impl;

import com.liveppt.models.dao.UserAccess;
import com.liveppt.services.UserService;
import com.liveppt.utils.exception.user.UserException;
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
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader regist(UserReader userReader) throws UserException {
        return UserAccess.create(userReader);
    }

    /**
     * 用户登陆
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader login(UserReader userReader) throws UserException {
        return UserAccess.login(userReader);
    }

    /**
     * 修改密码
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader updatePassword(UserReader userReader) throws UserException {
    	return UserAccess.updatePassword(userReader);
    }

    /**
     * 修改用户名
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader updateDisplay(UserReader userReader) throws UserException {
    	return UserAccess.updateDisplay(userReader);
    }
}
