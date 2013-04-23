package com.fever.liveppt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import com.avaje.ebean.validation.NotNull;

@Entity
public class Meeting extends Model {

	@Id
	public Long id;
	
	@ManyToOne
	@Constraints.Required
	public Ppt ppt;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@Constraints.Required
	public User founder;
	
	@NotNull
	@Constraints.Required
	public String topic = "";
	
	@NotNull
	public Long currentPageIndex = (long) 1;
	
	@OneToMany
	public List<Attender> attenders;
	
	public static Finder<Long, Meeting> find = new Finder<Long, Meeting>(Long.class,
			Meeting.class);
}
