package controllers.cold;

import play.*;
import play.mvc.*;

import views.html.*;

/**
 * 
 * Author 
 */

public class Application extends Controller {
  
  	/**
  	 * [index description]
  	 * @return [description]
  	 * last modified XXX
  	 */
    public static Result index() {
        return ok(index.render());
    }

    public static Result signup(){
        return ok(signup.render());
    }

    public static Result list(){
        return ok(list.render());
    }
    
    public static Result login(){
        return ok(login.render());
    }

    public static Result myppt(){
        String name = ctx().session().get("email");
        return ok(myppt.render(name));
    }
}
