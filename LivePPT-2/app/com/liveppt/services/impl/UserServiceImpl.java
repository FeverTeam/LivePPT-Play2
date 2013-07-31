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
     * 产生UserR类
     * @param params
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader genUserR(Map<String, String[]> params) {
        //TODO 添加错误类检验抛出

        UserReader user = new UserReader();
        System.out.println("genUserR");
        System.out.println(params.get("email")[0]);
        user.email = params.get("email")[0];
        user.password = params.get("password")[0];
        user.display = params.get("display")[0];
        return user;
    }

    /**
     * 产生Json
     * @param user
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserJson genJson(UserReader user) {
        //TODO 错误检查抛出
        UserJson userJson = new UserJson(user.email,user.password,user.display);
        return userJson;
    }
    /*
     * 登陆检查
     */
    public boolean loginCheck(String email, String password){
		User user = User.find.where().eq("email", email).findUnique();
		if (user == null) {
			// 用户不存在
			return false;
		} else {
			// 用户存在
			// 验证用户密码
			if (UserAccess.isPasswordCorrect(email, password)) {
				return true;
			} else {

				return false;
			}
		}
		
	}
    /**
     * 用户注册
     * @param user
     * @return
     * last modified 黎伟杰
     */
    @Override
    public UserReader regist(UserReader user) {
        //TODO 错误检查抛出
        return UserAccess.create(user);
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
