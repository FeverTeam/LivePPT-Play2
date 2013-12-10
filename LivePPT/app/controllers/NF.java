package controllers;

import com.fever.liveppt.models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import views.html.*;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-12-9
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class NF extends Controller {

    /**
     * 首页
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result index() {
        User user = CheckLoginAction.getUser(ctx());
        return ok(index.render(user));
    }

    /**
     * 应用下载页面
      * @return
     */
    @With(CheckLoginAction.class)
    public static Result apps() {
        User user = CheckLoginAction.getUser(ctx());
        return ok(apps.render(user));
    }

    /**
     * 关于页面
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result about() {
        User user = CheckLoginAction.getUser(ctx());
        return ok(about.render(user));
    }

}
