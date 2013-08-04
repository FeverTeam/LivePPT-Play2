package com.liveppt.services;

import com.liveppt.utils.exception.params.ParamsException;
import com.liveppt.utils.models.UserJson;

import java.util.Map;

/**
 * 用户接口
 * author 黎伟杰，黄梓财
 */
public interface UserService {

    public UserJson regist(Map<String, String[]> params) throws ParamsException;

    public UserJson login(Map<String, String[]> params) throws ParamsException;

    public UserJson updatePassword(Map<String, String[]> params) throws ParamsException;

    public UserJson updateDisplay(Map<String, String[]> params) throws ParamsException;
    

}
