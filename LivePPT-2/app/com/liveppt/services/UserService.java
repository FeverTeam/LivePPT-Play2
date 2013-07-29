package com.liveppt.services;

import com.liveppt.utils.UserJson;
import com.liveppt.utils.UserR;

import java.util.Map;

/**
 * 用户接口
 * author 黎伟杰
 */
public interface UserService {

    public UserR genUserR(Map<String, String[]> params);

    public UserJson genJson(UserR user);

    public UserR regist(UserR user);

}
