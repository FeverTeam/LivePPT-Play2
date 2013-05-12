package com.fever.liveppt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.codehaus.jackson.node.ObjectNode;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Http.Session;

/**
 * 用户类
 * 
 * @author 梁博文
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

	@OneToMany(cascade=CascadeType.ALL)
	public List<Ppt> ppts;

	@OneToMany(cascade=CascadeType.ALL)
	public List<Meeting> myFoundedMeeting;
	
	@OneToMany(cascade=CascadeType.ALL)
	public List<Attender> attendents;

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
			User.class);

	public User(String email, String password, String displayName) {
		this.email = email;
		this.password = password;
		this.displayname = displayName;
	}

	public static boolean isExistedByEmail(String email) {
		List<User> existedUsers = User.find.where().eq("email", email)
				.findList();
		if (existedUsers.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static User isPasswordCorrect(String email, String password) {
		List<User> targetUser = User.find.where().eq("email", email)
				.eq("password", password).findList();
		if (targetUser.size() == 1) {
			return targetUser.get(0);
		} else {
			return null;
		}
	}

	public static Long genUserIdFromSession(Session sess) {
		String email = sess.get("email");
		List<User> users = User.find.where().eq("email", email).findList();
		if (users.size() > 0) {
			return users.get(0).id;
		}
		return null;
	}
	
	public ObjectNode toJsonNode(){
		ObjectNode resultJson = Json.newObject();
		resultJson.put("userId", this.id);
		resultJson.put("displayName", this.displayname);
		resultJson.put("email", this.email);
		return resultJson;
	}

}
