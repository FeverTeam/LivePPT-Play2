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
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public UserReader create(UserReader userReader) throws UserException {
        //创建用户
        User user = User.find.where().eq("email",userReader.getEmail()).findUnique();
        if (user==null) {
            user = new User(userReader);
            user.save();
            userReader.setId(user.id);
            return userReader;
        } else {
            throw new UserExistedException();
        }
    }

    /**
     * 登录并反馈信息
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public UserReader login(UserReader userReader) throws UserException {
        //根据email查找用户
        User user = User.find.where().eq("email",userReader.getEmail()).findUnique();
        if (user==null){
            //用户不存在
            throw new EmailNotExistedException();
        } else {

            //检查密码
            if (userReader.getPassword().equals(user.password)) {
                //TODO 应该有User生成，补充ppt，meeting等信息
                userReader.setId(user.id);
                return userReader;
            } else {
                throw new PasswordErrorException();
            }
        }
    }

    /**
     * 检查账户是否存在
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public boolean isEmailExist(String email) throws UserException {
        User user = User.find.where().eq("email", email).findUnique();
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
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public boolean isPasswordCorrect(Long id,String password) throws UserException {
    	User user = User.find.byId(id);
    	if(user.password.equals(password)){
    	//密码正确
    	return true;
    	}
       else return false;
    	
    } 
    /**
     * 删除用户
     * @param
     * @return
     * last modified   黎伟杰
     */
    static public void delete(Long id) throws UserException {
    	User user  = User.find.byId(id);
    	user.delete();
    }
    
    /**
     * 更新Password
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public UserReader updatePassword(UserReader userReader) throws UserException {
        //根据id查找用户
    	User user = User.find.byId(userReader.getId());
        if (userReader.getPassword().equals(user.password)) {
            user.password=userReader.getNewPassword();
            user.save();
            userReader.setPassword(user.password);
            return userReader;
        } else {
            throw new PasswordErrorException();
        }
	}
    
    /**
     * 更新display
     * @param
     * @return
     * last modified 黎伟杰
     */
    static public UserReader updateDisplay(UserReader userReader) throws UserException {
        //根据id查找用户
        User user = User.find.byId(userReader.getId());
        if (userReader.getPassword().equals(user.password)) {
            user.display=userReader.getNewDisplay();
            user.save();
            userReader.setDisplay(user.display);
        } else {
            throw new PasswordErrorException();
        }
		return userReader;
	}

}
