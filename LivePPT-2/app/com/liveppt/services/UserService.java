package com.liveppt.services;

import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;

/**
 * 用户接口
 * author 黎伟杰，黄梓财
 */
public interface UserService {

    public UserReader regist(UserReader userReader) throws UserException;

    public UserReader login(UserReader userReader) throws UserException;

    public UserReader updatePassword(UserReader userReader) throws UserException;

    public UserReader updateDisplay(UserReader userReader) throws UserException;

}
