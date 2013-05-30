package com.fever.liveppt.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.validation.NotNull;

/**
 * 参与会议关系类
 * @author 梁博文
 *
 */
@Entity
public class Attender extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7569560456239630312L;

	@Id
	public Long id;
	
	@NotNull
	@ManyToOne
	public Meeting meeting;
	
	@NotNull
	@ManyToOne
	public User user;
	
	public static Finder<Long, Attender> find = new Finder<Long, Attender>(Long.class,
			Attender.class);
	
	public Attender(Meeting meeting, User user){
		this.meeting = meeting;
		this.user = user;
	}
}
