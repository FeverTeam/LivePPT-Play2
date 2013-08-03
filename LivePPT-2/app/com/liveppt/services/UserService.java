package com.liveppt.services;

import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;

/**
 * 用户接口
 * author 黎伟杰
 */
public interface UserService {

//    public UserReader genUserR(Map<String, String[]> params);

//    public UserJson genJson(UserReader user);

    public UserJson regist(Map<String, String[]> params);

    public UserJson login(Map<String, String[]> params);

    public UserJson updatePassword(Map<String, String[]> params);
    
    public UserJson updateDisplayname(Map<String, String[]> paramse);
    

}
