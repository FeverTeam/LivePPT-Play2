package com.fever.liveppt.models;

import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import play.Logger;
import play.db.ebean.*;
import play.data.validation.*;
import play.mvc.Http.Session;

/**
 * 用户类
 * 
 * @author Bowen Liang
 * 
 */
@Entity
public class User extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public String email;

	@Constraints.Required
	public String password;

	public String displayname;

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
			User.class);

	public User(String email, String password, String displayname) {
		this.email = email;
		this.password = password;
		this.displayname = displayname;
	}

	public static boolean isExistedByEmail(String email) {
		List<User> existedUsers = User.find.where().eq("email", email).findList();
		if (existedUsers.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static User isPasswordCorrect(String email, String password){
		List<User> targetUser = User.find.where().eq("email", email).eq("password", password).findList();
		if (targetUser.size() == 1) {
			return targetUser.get(0);
		} else {
			return null;
		}
	}
	
	public static Long genUserIdFromSession(Session sess){
		String email = sess.get("email");
		List<User> users = User.find.where().eq("email", email).findList();
		if (users.size()>0){
			return users.get(0).id;
		}
		return null;
	}

}
