package com.liveppt.models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Id;

/**
 * 用户类
 * author 黎伟杰
 */

public class User extends Model {

    @Id
    public Long id;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String displayname;

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
            User.class);

    public User(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayname = displayName;
    }

}
