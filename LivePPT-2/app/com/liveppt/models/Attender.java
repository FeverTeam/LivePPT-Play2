/**
 * @param
 * @return
 */
package com.liveppt.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import play.db.ebean.Model;

import com.avaje.ebean.validation.NotNull;


/**
 * @author Zijing Lee2013-8-21
 *
 */
@Entity
public class Attender extends Model {

	@Id
	public Long id;
	
	@NotNull
	@JoinColumn(name = "meeting_id")
	@ManyToOne
	public Meeting meeting;
	
	@NotNull
	@JoinColumn(name = "user_id")
	@ManyToOne
	public User user;
	
	public static Finder<Long, Attender> find = new Finder<Long, Attender>(Long.class,
			Attender.class);
	
	public Attender(Meeting meeting, User user){
		this.meeting = meeting;
		this.user = user;
	}
	
	public Attender(Long meetingId, Long userId){
		this.meeting = Meeting.find.byId(meetingId);
		this.user = User.find.byId(userId);
	}
}
