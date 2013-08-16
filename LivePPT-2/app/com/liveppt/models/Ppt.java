/**
 * @param
 * @return
 */
package com.liveppt.models;



import java.util.Date;

import com.liveppt.utils.models.PptReader;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.avaje.ebean.validation.NotNull;

/**
 * @author Zijing Lee2013-8-16
 * Ppt类
 */
@Entity
public class Ppt extends Model{
	@Id
	public Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User owner;

	@NotNull
	@Constraints.Required
	public String fileName;

	@NotNull
	@Constraints.Required
	public Date time;

	@NotNull
	@Constraints.Required
	public Long fileSize;

	@NotNull
	@Constraints.Required
	public boolean isConverted;

	@NotNull
	@Constraints.Required
	public int pagecount = 0;

	public static Finder<Long, Ppt> find = new Finder<Long, Ppt>(Long.class,
			Ppt.class);

	public Ppt(Long userId, String title, Date time, String storeKey,
			Long fileSize,int pageCount) {
		User user = User.find.byId(userId);
		this.owner = user;
		this.fileName = title;
		this.time = time;
		this.fileSize = fileSize;
		this.isConverted = false;
		this.pagecount = pageCount;
	}
   
	public Ppt(PptReader pptReader)
	{
		User user = User.find.byId(pptReader.userId);
		this.owner = user;
		this.fileName = pptReader.fileName;
		this.time = pptReader.time;
		this.fileSize = pptReader.fileSize;
		this.isConverted = false;
		this.pagecount = pptReader.pageCount;
	}
	
}
