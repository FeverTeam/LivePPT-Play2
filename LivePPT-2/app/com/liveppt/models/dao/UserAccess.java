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
     * last modified 黄梓财
     */
    static public UserJson create(Map<String, String[]> params) throws UserException {
        UserReader userReader = genUserReader(params);
        if (params.get("display")[0]==null) throw  new DisplayNotFoundException();
        userReader.display = params.get("display")[0];
        User user = User.find.where().eq("email",userReader.email).findUnique();
        UserJson userJson = genUserJson(userReader);
        if (user==null) {
            user = new User(userReader);
            user.save();
            userReader.id = user.id;
            return userJson.putStatus(StatusCode.NONE);            
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
        UserReader userReader = genUserReader(params);
        User user = User.find.where().eq("email",userReader.email).findUnique();
        UserJson userJson = genUserJson(userReader);
        if (user==null){
            //用户不存在
            throw new EmailNotExistedException();
        } else {
            if (userJson.getPassword().equals(user.password)) {
                //TODO 应该有User生成，补充ppt，meeting等信息
                return userJson.putStatus(StatusCode.NONE).putDisplay(user.display);
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
     * last modified 黎伟杰
     */
    static public boolean isPasswordCorrect(Map<String,String[]> params) throws UserException {
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
     * last modified   黎伟杰
     */
    static public void delete(Map<String,String[]> params) throws UserException {
    	UserReader userReader = genUserReader(params);
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
    	UserReader userReader = genUserReader(params);
        if (params.get("newPassword")[0]==null) throw  new NewPasswordNotFoundException();
        String newPassword = params.get("newPassword")[0];
    	User user = User.find.where().eq("email", userReader.email).findUnique();
        if (userReader.password.equals(user.password)) {
            user.password=newPassword;
            user.save();
            userReader.password = user.password;
        } else {
            throw new PasswordErrorException();
        }
		return genUserJson(userReader);	
	}
    
    /**
     * 更新display
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserJson updateDisplay(Map<String,String[]> params) throws UserException {
        //TODO 将取参数的域修改为静态字符变量
    	UserReader userReader = genUserReader(params);
        if (params.get("display")[0]==null) throw  new DisplayNotFoundException();
        userReader.display = params.get("display")[0];
        if (params.get("newDisplay")[0]==null) throw  new NewDisplayNotFoundException();
        String newDisplay = params.get("newDisplay")[0];
        User user = User.find.where().eq("email", userReader.email).findUnique();
        if (userReader.password.equals(user.password)) {
            user.display=newDisplay;
            user.save();
//            System.out.println(user.password);
//            user = User.find.where().eq("email", userReader.email).findUnique();
//            System.out.println(user.password);
            userReader.display = user.display;
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

    /**
     * 产生UserR类
     * @param params
     * @return
     * last modified 黎伟杰
     */
    static public UserReader genUserReader(Map<String, String[]> params) throws UserException {
        UserReader user = new UserReader();
        System.out.println("genUserR");
        System.out.println(params.get("email")[0]);
        if (params.get("email")[0]==null) throw  new EmailNotFoundException();
        user.email = params.get("email")[0];
        if (params.get("password")[0]==null) throw  new PasswordNotFoundException();
        user.password = params.get("password")[0];
        return user;
    }
}
