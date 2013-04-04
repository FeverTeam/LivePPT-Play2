package controllers;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Http.Session;
import play.mvc.Result;

public class CheckLoginAction extends Action.Simple {

	public Result call(Http.Context ctx) throws Throwable {		
		//获取session
		Session sess = ctx.session();
		
		String username = sess.get("user");
		if (username != null) {
			return Frontend.index();
		} else {
			return Frontend.login();
		}
	}
}
