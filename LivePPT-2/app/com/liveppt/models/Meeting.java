/**
 * @param
 * @return
 */
package com.liveppt.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

import com.avaje.ebean.validation.NotNull;
import com.liveppt.utils.models.MeetingReader;


/**
 * @author Zijing Lee2013-8-21
 *
 */
@Entity
public class Meeting extends Model{
	
	@Id
	public Long id;

	@ManyToOne
	@JoinColumn(name = "ppt_id")
	@Constraints.Required
	public Ppt ppt;

	@ManyToOne
	@JoinColumn(name = "user_id")
	@Constraints.Required
	public User founder;

	@NotNull
	@Constraints.Required
	public String topic = "";

	@NotNull
	public Long currentPageIndex = (long) 1;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Attender> attenders;

	public static Finder<Long, Meeting> find = new Finder<Long, Meeting>(
			Long.class, Meeting.class);
	
	public Meeting(Long pptId,Long userId,String topic,Long currentPageIndex)
	{
		Ppt ppt = Ppt.find.byId(pptId);
		this.ppt = ppt;
		User founder = User.find.byId(userId);
		this.founder = founder;
		this.topic = topic;
		this.currentPageIndex = currentPageIndex;
	}
	
	public Meeting(MeetingReader meetingReader)
	{
		Ppt ppt = Ppt.find.byId(meetingReader.getPptId());
		this.ppt = ppt;
		User founder = User.find.byId(meetingReader.getUserId());
		this.founder = founder;
		this.topic = meetingReader.getTopic();
		this.currentPageIndex = meetingReader.getCurrentPageIndex();
		
	}

}
