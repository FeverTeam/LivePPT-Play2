package controllers;

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
	
	public static Result signup(){
		return ok(signup.render(null));
	}

}
