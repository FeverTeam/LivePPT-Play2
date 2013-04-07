package controllers;

import java.util.List;

import com.fever.liveppt.models.Ownership;
import com.fever.liveppt.models.User;

import play.*;
import play.mvc.*;

import views.html.*;

public class Frontend extends Controller {

	public static Result index() {
		return ok(index.render(null));
	}

	@With(CheckLoginAction.class)
	public static Result login() {
		session("name", "lbw");
		return ok(login.render(null));
	}
	
	@With(CheckLoginAction.class)
	public static Result signup(){
		return ok(signup.render(null));
	}
	
	
	@With(CheckLoginAction.class)
	public static Result myppt(){
		String displayname = session("displayname");
		Long userId = User.genUserIdFromSession(ctx().session());
		List<Ownership> ownerships = Ownership.find.where().where().eq("userid", userId).findList();
		return ok(myppt.render(null, displayname, ownerships));
	}
}
