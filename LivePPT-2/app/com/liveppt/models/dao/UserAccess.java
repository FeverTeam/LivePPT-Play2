package com.liveppt.models.dao;




import com.liveppt.models.User;
import com.liveppt.utils.models.UserReader;

/**
 * description
 * author 黎伟杰
 */
public class UserAccess {

    /**
     * 创建新的用户
     * @param userReader
     * @return
     * last modified黎伟杰l
     */
    static public UserReader create(UserReader userReader){
        //TODO 重名检查
        User user = new User(userReader.email, userReader.password, userReader.display);
        user.save();
        userReader.id = user.id;
        return userReader;
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
		user.displayname=newDisplayname;
		userReader.display = user.displayname;
		user.save();
		return userReader;
	}
}
