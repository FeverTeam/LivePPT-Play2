package com.fever.liveppt.models;

import org.codehaus.jackson.node.ObjectNode;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.libs.Json;
import play.mvc.Http.Session;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户类
 *
 * @author 梁博文
 */
@Entity
public class User extends Model {

    /**
     *
     */
    private static final long serialVersionUID = 4396006371291229467L;

    @Id
    public Long id;

    @Constraints.Required
    public String email;

    @Constraints.Required
    public String password;

    public String displayname;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Ppt> ppts;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Meeting> myFoundedMeeting;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Attender> attendents;

    public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
            User.class);

    public User(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayname = displayName;
    }

    public static boolean isExistedByEmail(String email) {
        User user = User.find.where().eq("email", email).findUnique();
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmailFormatValid(String email) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");
        Matcher m = p.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static User isPasswordCorrect(String email, String password) {
        User user = User.find.where().eq("email", email)
                .eq("password", password).findUnique();
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    public static User genUserFromSession(Session sess) {
        String email = sess.get("email");
        User user = User.find.where().eq("email", email).findUnique();
        if (user != null) {
            return user;
        }
        return null;
    }

    public ObjectNode toJsonNode() {
        ObjectNode resultJson = Json.newObject();
        resultJson.put("userId", this.id);
        resultJson.put("displayName", this.displayname);
        resultJson.put("email", this.email);
        return resultJson;
    }

    public ObjectNode toJson() {
        ObjectNode resultJson = Json.newObject();
        resultJson.put("displayName", this.displayname);
        resultJson.put("email", this.email);
        return resultJson;
    }

}
