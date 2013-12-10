package controllers;

import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import play.Logger;
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

    /**
     * 我的幻灯
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result ppts() {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);
        Logger.info("ppts");
        return ok(ppts.render(user, token));
    }


    /**
     * 我的幻灯
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result pptPreview(Long pptid) {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        Ppt ppt = Ppt.find.byId(pptid);
        return ok(pptPreview.render(ppt, user, token));
    }
}