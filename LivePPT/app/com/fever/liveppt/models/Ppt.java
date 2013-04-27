package com.fever.liveppt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.validation.NotNull;

import play.Logger;
import play.db.ebean.*;
import play.data.validation.*;

/**
 * PPT 类
 * 
 * @author 梁博文
 * 
 */
@Entity
public class Ppt extends Model {

	@Id
	public Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User owner;

	@NotNull
	@Constraints.Required
	public String title;

	@NotNull
	@Constraints.Required
	public Date time;
	
	@NotNull
	@Constraints.Required
	public String storeKey;
	
	@NotNull
	@Constraints.Required
	public Long fileSize;
	
	@NotNull
	@Constraints.Required
	public boolean isConverted;
	
	@NotNull
	@Constraints.Required
	public int pagecount = 0;
	
	@OneToMany
	List<Meeting> meetings;

	public static Finder<Long, Ppt> find = new Finder<Long, Ppt>(Long.class,
			Ppt.class);
	
	public Ppt(Long userid, String title, Date time, String storeKey, Long fileSize){
		User user = User.find.byId(userid);
		this.owner = user;
		this.title = title;
		this.time = time;
		this.storeKey = storeKey;
		this.fileSize = fileSize;
		this.isConverted = false;
		this.pagecount = 0;
	}
}
