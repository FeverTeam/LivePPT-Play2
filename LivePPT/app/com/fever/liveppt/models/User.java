package com.fever.liveppt.models;

import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import play.db.ebean.*;
import play.data.validation.*;

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
//		Query<User> q = Ebean.createQuery(User.class);
		List<User> existedUsers = User.find.where().eq("email", email).findList();
		if (existedUsers.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
