/**
 * @param
 * @return
 */
package com.liveppt.models;



import java.util.Date;
import java.util.List;

import com.liveppt.utils.models.PptReader;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
	public String storeKey;

	@NotNull
	@Constraints.Required
	public int pagecount ;
    
	@OneToMany(cascade = CascadeType.ALL)
	public List<Meeting> meetings;
	
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
		User user = User.find.byId(pptReader.getUserId());
		this.owner = user;
		this.fileName = pptReader.getFileName();
		this.time = pptReader.getTime();
		this.fileSize = pptReader.getFileSize();
		this.isConverted = false;
		this.pagecount = pptReader.getPageCount();
	}
	
}
