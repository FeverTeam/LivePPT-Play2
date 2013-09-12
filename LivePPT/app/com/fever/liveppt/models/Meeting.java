package com.fever.liveppt.models;

import com.avaje.ebean.validation.NotNull;
import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.util.List;

/**
 * 会议类
 *
 * @author 梁博文
 */
@Entity
public class Meeting extends Model {

    /**
     *
     */
    private static final long serialVersionUID = -8840227542777988716L;

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

    public ObjectNode toMyMeetingJson() {
        ObjectNode resultJson = Json.newObject();
        resultJson.put("meetingId", this.id);
        resultJson.put("pptId", this.ppt.id);
        resultJson.put("founder", "null");
        resultJson.put("topic", this.topic);
        return resultJson;
    }

    public ObjectNode toMeetingJson(){
        ObjectNode resultJson = Json.newObject();
        resultJson.put("meetingId", this.id);
        resultJson.put("pptId", this.ppt.id);
        resultJson.put("founder", this.founder.toJson());
        resultJson.put("topic", this.topic);
        return resultJson;
    }

}
