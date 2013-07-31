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
    static public UserR create(UserR userR){
        //TODO 重名检查
        User user = new User(userR.email,userR.password,userR.display);
        user.save();
        userR.id = user.id;
        return userR;
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
    static public UserR updateByEmail(UserR userR,String newEmail)
	{   
    	User user = User.find.where().eq("email", userR.email).findUnique();
		user.email=newEmail;
		userR.email = user.email;
		user.save();
		return userR;
	}
    /*
     * update by password
     */
    static public UserR updateByPassword(UserR userR,String newPassword)
	{   
    	User user = User.find.where().eq("password", userR.password).findUnique();
		user.password=newPassword;
		userR.password = user.password;
		user.save();
		return userR;
	}
    /*
     * update by disoplay name
     */
    static public UserR updateByDisplayname(UserR userR,String newDisplayname)
	{   
    	User user = User.find.where().eq("displayname", userR.display).findUnique();
		user.displayname=newDisplayname;
		userR.display = user.displayname;
		user.save();
		return userR;
	}
}
