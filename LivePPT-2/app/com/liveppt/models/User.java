package com.liveppt.models;

import java.util.List;


import com.fever.liveppt.models.Attender;
import com.liveppt.utils.models.UserReader;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * 用户类
 * author 黎伟杰
 */

@Entity
public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String display;
    
    @OneToMany(cascade = CascadeType.ALL)
	public List<Ppt> ppts;
    
    @OneToMany(cascade = CascadeType.ALL)
    public List<Meeting> meetings;
    
    @OneToMany(cascade = CascadeType.ALL)
	public List<Attender> attendents;
    
    public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
            User.class);

    public User(String email, String password, String display) {
        this.email = email;
        this.password = password;
        this.display = display;
    }

    public User(UserReader userReader) {
        this.email = userReader.email;
        this.password = userReader.password;
        this.display = userReader.display;
    }

}
