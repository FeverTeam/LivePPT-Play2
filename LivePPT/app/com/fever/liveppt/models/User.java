package com.fever.liveppt.models;

import javax.persistence.*;

import play.db.ebean.*;
import play.data.validation.*;

@Entity
public class User extends Model {

	@Id
	public Long id;
	
	@Constraints.Required
	public String email;
	
	@Constraints.Required
	public String password;
	
	public String displayname;
	
	public static Finder<Long,User> find = new Finder<Long,User>(
		    Long.class, User.class
		  ); 
	
	public User(String email, String password, String displayname){
		this.email = email;
		this.password = password;
		this.displayname = displayname;
	}

}
