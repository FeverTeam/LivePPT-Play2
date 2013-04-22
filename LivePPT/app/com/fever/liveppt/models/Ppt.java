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
 * PPT
 * 
 * @author Bowen Liang
 * 
 */
@Entity
public class Ppt extends Model {

	@Id
	public Long id;

	@NotNull
	@Constraints.Required
	public Long userId;

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
		this.userId = userid;
		this.title = title;
		this.time = time;
		this.storeKey = storeKey;
		this.fileSize = fileSize;
		this.isConverted = false;
		this.pagecount = 0;
	}
}
