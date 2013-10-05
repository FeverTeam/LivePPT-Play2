package com.fever.liveppt.models;


import com.fasterxml.jackson.databind.node.ObjectNode;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * PPT 类
 *
 * @author 梁博文
 */
@Entity
public class Ppt extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 7122690074005665105L;

    @Id
    public Long id;

    @Constraints.Required
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User owner;

    @Constraints.Required
    public String title;

    @Constraints.Required
    public Date time;

    @Constraints.Required
    public String storeKey;

    @Constraints.Required
    public Long fileSize;

    @Constraints.Required
    public boolean isConverted;

    @Constraints.Required
    public int pagecount = 0;

    @OneToMany
    List<Meeting> meetings;

    public static Finder<Long, Ppt> find = new Finder<Long, Ppt>(Long.class, Ppt.class);

    public Ppt(Long userid, String title, Date time, String storeKey, Long fileSize) {
        User user = User.find.byId(userid);
        this.owner = user;
        this.title = title;
        this.time = time;
        this.storeKey = storeKey;
        this.fileSize = fileSize;
        this.isConverted = false;
        this.pagecount = 0;
    }

    // 默认中国时间
    public ObjectNode toJsonNode() {
        return toJsonNode("+8");
    }

    // 指定时区
    // Sample:timeZone = "+8";
    public ObjectNode toJsonNode(String timeZone) {
        ObjectNode pptNode = Json.newObject();

        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("GMT" + timeZone));
        pptNode.put("time", sdf.format(this.time).toString());

        pptNode.put("pptId", this.id);
        pptNode.put("title", this.title);
        pptNode.put("size", this.fileSize);
        pptNode.put("pageCount", this.pagecount);
        pptNode.put("isConverted", this.isConverted);
        return pptNode;
    }
}
