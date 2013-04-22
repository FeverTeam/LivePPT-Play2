package com.fever.liveppt.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
import play.db.ebean.Model.Finder;


@Entity
public class Attender extends Model {

	@Id
	public Long id;
	
	@ManyToOne
	public Meeting meeting;
	
	@ManyToOne
	public User user;
	
	public static Finder<Long, Attender> find = new Finder<Long, Attender>(Long.class,
			Attender.class);
}
