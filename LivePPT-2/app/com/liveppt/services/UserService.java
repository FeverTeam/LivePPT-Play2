package com.liveppt.services;

import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;

/**
 * 用户接口
 * author 黎伟杰
 */
public interface UserService {

    public UserReader genUserR(Map<String, String[]> params);

    public UserJson genJson(UserReader user);

    public UserReader regist(UserReader user);
    
    public boolean loginCheck(String email, String password);
    
    public UserReader updateByEmail(UserReader user, String newEmail);

    public UserReader updateByPassword(UserReader userReader, String newPassword);
    
    public UserReader updateByDisplayname(UserReader userReader, String newDisplayname);
    

}
