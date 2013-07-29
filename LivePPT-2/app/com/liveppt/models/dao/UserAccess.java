package com.liveppt.models.dao;

import com.liveppt.models.User;
import com.liveppt.utils.UserR;

/**
 * description
 * author 黎伟杰
 */
public class UserAccess {

    /**
     * 创建新的用户
     * @param userR
     * @return
     * last modified黎伟杰l
     */
    public UserR create(UserR userR){
        //TODO 重名检查
        User user = new User(userR.email,userR.password,userR.display);
        user.save();
        userR.id = user.id;
        return userR;
    }

}
