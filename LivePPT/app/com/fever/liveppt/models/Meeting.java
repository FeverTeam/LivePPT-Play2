package com.fever.liveppt.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.codehaus.jackson.node.ObjectNode;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;

import com.avaje.ebean.validation.NotNull;

/**
 * 会议类
 * @author 梁博文
 *
 */
@Entity
public class Meeting extends Model {

	@Id
	public Long id;

	@ManyToOne
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

	@OneToMany
	public List<Attender> attenders;

	public static Finder<Long, Meeting> find = new Finder<Long, Meeting>(
			Long.class, Meeting.class);

	public ObjectNode toJson() {
		ObjectNode resultJson = Json.newObject();
		resultJson.put("meetingId", this.id);
		resultJson.put("ppt", this.ppt.toJsonNode());
		resultJson.put("founder", this.founder.toJsonNode());
		resultJson.put("topic", this.topic);
		return resultJson;
	}
}
