package com.liveppt.models.dao;

import com.liveppt.models.User;
import com.liveppt.utils.StatusCode;
import com.liveppt.utils.models.UserJson;
import com.liveppt.utils.models.UserReader;

import java.util.Map;

/**
 * description
 * author 黎伟杰
 */
public class UserAccess {

    /**
     * 创建新的用户
     * @param params
     * @return
     * last modified黎伟杰l
     */
    static public UserJson create(Map<String, String[]> params){
        //TODO 重名检查
        UserReader userReader = genUserReader(params);
        User user = new User(userReader);
        user.save();
        userReader.id = user.id;
        return genUserJson(userReader);
    }

    /**
     * 登陆并反馈信息
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson login(Map<String, String[]> params){
        UserReader userReader = genUserReader(params);
        User user = User.find.where().eq("email",userReader.email).findUnique();
        UserJson userJson = genUserJson(userReader);
        if (user==null){
            //用户不存在
            //TODO 抛出异常,用户不存在错误代码补充
            return userJson.putStatus(StatusCode.TODO);
        } else {
            if (userJson.equals(user.password)) {
                //TODO 应该有User生成，补充ppt，meeting等信息
                return userJson.putStatus(StatusCode.NONE);
            } else {
                return userJson.putStatus(StatusCode.USER_PASSWORD_ERROR);
            }
        }

    }

    /**
     * 检查账户是否存在
     * @param params
     * @return
     * last modified Zijing Lee
     */
    static public boolean isEmailExist(Map<String, String[]> params){
        UserReader userReader = genUserReader(params);
        User user = User.find.where().eq("email", userReader.email).findUnique();
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
     * last modified Zijing Lee
     */
    static public boolean isPasswordCorrect(Map<String,String[]> params){
    	UserReader userReader = genUserReader(params);
    	User user = User.find.where().eq("email", userReader.email).findUnique();
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
     * last modified Zijing Lee
     */
    static public void delete(Map<String,String[]> params){
    	UserReader userReader = genUserReader(params);
    	User user  = User.find.byId(userReader.id);
    	user.delete();
    }
    
    /**
     * 更新Email
     * @param params,newEmail
     * @return
     * last modified Zijing Lee
     */
    static public UserJson updateByEmail(Map<String,String[]> params,String newEmail)
	{   
    	UserReader userReader = genUserReader(params);
    	User user = User.find.where().eq("email", userReader.email).findUnique();
		user.email=newEmail;
		userReader.email = user.email;
		user.save();
		return genUserJson(userReader);		
	}
    
    /**
     * 更新Password
     * @param params,newPassword
     * @return
     * last modified Zijing Lee
     */
    static public UserJson updateByPassword(Map<String,String[]> params,String newPassword)
	{   
    	UserReader userReader = genUserReader(params);
    	User user = User.find.where().eq("password", userReader.password).findUnique();
		user.password=newPassword;
		userReader.password = user.password;
		user.save();
		return genUserJson(userReader);	
	}
    
    /**
     * 更新display
     * @param params,newDisplay
     * @return
     * last modified Zijing Lee
     */
    static public UserJson updateByDisplay(Map<String,String[]> params,String newDisplay)
	{   
    	UserReader userReader = genUserReader(params);
    	User user = User.find.where().eq("display", userReader.display).findUnique();
		user.display=newDisplay;
		userReader.display = user.display;
		user.save();
		return genUserJson(userReader);	
	}

    /**
     * 产生Json
     * @param user
     * @return
     * last modified 黎伟杰
     */
    static public UserJson genUserJson(UserReader user) {
        //TODO 错误检查抛出
        UserJson userJson = new UserJson(user.email,user.password,user.display);
        return userJson;
    }

    /**
     * 产生UserR类
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserReader genUserReader(Map<String, String[]> params) {
        //TODO 添加错误类检验抛出

        UserReader user = new UserReader();
        System.out.println("genUserR");
        System.out.println(params.get("email")[0]);
        user.email = params.get("email")[0];
        user.password = params.get("password")[0];
        user.display = params.get("display")[0];
        return user;
    }
}
