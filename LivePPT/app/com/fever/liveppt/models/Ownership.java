package com.fever.liveppt.models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;

import play.Logger;
import play.db.ebean.*;
import play.data.validation.*;

/**
 * 用户类
 * 
 * @author Bowen Liang
 * 
 */
@Entity
public class Ownership extends Model {

	@Id
	public Long id;

	@Constraints.Required
	public Long userid;

	@Constraints.Required
	public String title;

	@Constraints.Required
	public Date time;
	
	@Constraints.Required
	public String storeKey;
	
	@Constraints.Required
	public Long fileSize;

	public static Finder<Long, Ownership> find = new Finder<Long, Ownership>(Long.class,
			Ownership.class);
	
	public Ownership(Long userid, String title, Date time, String storeKey, Long fileSize){
		this.userid = userid;
		this.title = title;
		this.time = time;
		this.storeKey = storeKey;
		this.fileSize = fileSize;
	}
}
