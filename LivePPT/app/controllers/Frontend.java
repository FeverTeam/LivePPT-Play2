package controllers;

import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;
import views.html.index;
import views.html.login;
import views.html.myppt;
import views.html.signup;

import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;

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
		List<Ppt> ownerships = Ppt.find.where().where().eq("userId", userId).findList();
		return ok(myppt.render(null, displayname, ownerships));
	}
}
