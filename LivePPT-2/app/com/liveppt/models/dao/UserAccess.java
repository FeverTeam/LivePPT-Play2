package com.liveppt.models.dao;

import com.liveppt.models.User;
import com.liveppt.utils.StatusCode;
import com.liveppt.utils.exception.user.*;
import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;

/**
 * description
 * author 黎伟杰，黄梓财
 */
public class UserAccess {

    /**
     * 创建新的用户
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson create(Map<String, String[]> params) throws UserException {
        UserReader userReader = new UserReader(params);
        //填充需要信息和抛出异常
        userReader.setEmail().setPassword().setDisplay();
        //创建用户
        User user = User.find.where().eq("email",userReader.email).findUnique();
        if (user==null) {
            user = new User(userReader);
            user.save();
            userReader.id = user.id;
            UserJson userJson = genUserJson(userReader);
            return userJson;
        } else {
            throw new UserExistedException();
        }
    }

    /**
     * 登录并反馈信息
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson login(Map<String, String[]> params) throws UserException {
        UserReader userReader = new UserReader(params);
        //填充需要信息和抛出异常
        userReader.setEmail().setPassword();
        //根据email查找用户
        User user = User.find.where().eq(UserJson.KEY_EMAIL,userReader.email).findUnique();
        if (user==null){
            //用户不存在
            throw new EmailNotExistedException();
        } else {

            //检查密码
            if (userReader.password.equals(user.password)) {
                //TODO 应该有User生成，补充ppt，meeting等信息
                UserJson userJson = genUserJson(userReader);
                return userJson.putId(user.id);
            } else {
                throw new PasswordErrorException();
            }
        }
    }

    /**
     * 检查账户是否存在
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public boolean isEmailExist(Map<String, String[]> params) throws UserException {
        UserReader userReader = new UserReader(params).setEmail();
        User user = User.find.where().eq(UserJson.KEY_EMAIL, userReader.email).findUnique();
        if(user == null){
        // 用户不存在
        // 设置用户不存在状态码为1101
        //userReader.putStatus(1101);
        return false;
        }
        else return true;
    }
    
    /**
     * 检查密码是否正确
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public boolean isPasswordCorrect(Map<String,String[]> params) throws UserException {
    	UserReader userReader = new UserReader(params).setPassword();
    	User user = User.find.where().eq(UserJson.KEY_EMAIL, userReader.email).findUnique();
    	if(user.password.equals(userReader.password)){
    	//密码正确
    	return true;
    	}
       else return false;
    	
    } 
    /**
     * 删除用户
     * @param params
     * @return
     * last modified   黎伟杰
     */
    static public void delete(Map<String,String[]> params) throws UserException {
    	UserReader userReader = new UserReader(params).setId();
    	User user  = User.find.byId(userReader.id);
    	user.delete();
    }
    
    /**
     * 更新Password
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson updatePassword(Map<String,String[]> params) throws UserException {
    	UserReader userReader = new UserReader(params);
        //填充需要信息和抛出异常
        userReader.setId().setPassword().setNewPassword();
        //根据id查找用户
    	User user = User.find.byId(userReader.id);
        if (userReader.password.equals(user.password)) {
            user.password=userReader.newPassword;
            user.save();
            userReader.password = userReader.newPassword;
            return genUserJson(userReader);
        } else {
            throw new PasswordErrorException();
        }
	}
    
    /**
     * 更新display
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson updateDisplay(Map<String,String[]> params) throws UserException {
    	UserReader userReader = new UserReader(params);
        //填充需要信息和抛出异常
        userReader.setId().setDisplay().setNewDisplay().setPassword();
        //根据id查找用户
        User user = User.find.byId(userReader.id);
        if (userReader.password.equals(user.password)) {
            user.display=userReader.newDisplay;
            user.save();
            userReader.display = userReader.newDisplay;
        } else {
            throw new PasswordErrorException();
        }
		return genUserJson(userReader);	
	}

    /**
     * 产生UserJson
     * @param user
     * @return
     * last modified 黎伟杰
     */
    static public UserJson genUserJson(UserReader user) {
        UserJson userJson = new UserJson(user.email,user.password,user.display);
        return userJson;
    }


}
