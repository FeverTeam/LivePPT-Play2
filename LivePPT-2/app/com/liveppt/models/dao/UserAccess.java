package com.liveppt.models.dao;




import com.liveppt.models.User;
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
    
    /*
     * 密码检查
     */
    static public boolean isPasswordCorrect(String email,String password){
    	User user = User.find.where().eq("email", email).findUnique();
    	if(user.password.equals(password)){
    			return true;
    	}
       else return false;
    	
    }
   
    /*
     * 删除用户
     */
    static public void delete(Long userId){
    	User user  = User.find.byId(userId);
    	user.delete();
    }
    
    /*
     * update by email
     */
    static public UserReader updateByEmail(UserReader userReader,String newEmail)
	{   
    	User user = User.find.where().eq("email", userReader.email).findUnique();
		user.email=newEmail;
		userReader.email = user.email;
		user.save();
		return userReader;
	}
    /*
     * update by password
     */
    static public UserReader updateByPassword(UserReader userReader,String newPassword)
	{   
    	User user = User.find.where().eq("password", userReader.password).findUnique();
		user.password=newPassword;
		userReader.password = user.password;
		user.save();
		return userReader;
	}
    /*
     * update by disoplay name
     */
    static public UserReader updateByDisplayname(UserReader userReader,String newDisplayname)
	{   
    	User user = User.find.where().eq("displayname", userReader.display).findUnique();
		user.display=newDisplayname;
		userReader.display = user.display;
		user.save();
		return userReader;
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
