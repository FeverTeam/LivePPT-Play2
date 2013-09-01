package controllers;

import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security.Authenticated;
import play.mvc.Security.Authenticator;

@Authenticated
public class LoginAuthentication extends Authenticator {

    @Override
    public String getUsername(Context ctx) {
        return ctx.session().get("userId");
    }

    @Override
    public Result onUnauthorized(Context ctx) {
        return redirect(routes.Frontend.login()
        );
    }
}
