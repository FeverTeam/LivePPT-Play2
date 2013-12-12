package controllers;

import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import views.html.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: simonlbw
 * Date: 13-12-9
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class NF extends Controller {

    /**
     * 登录之后访问这个Action，用于将用于信息写入cookie
     * 必须提供以下参数
     * callbackUrl 需要重定向的位置
     * uemail 用户的email地址，作为用户ID
     * token 用户的token，用于调用其他接口
     *
     * @return
     */
    public static Result loginSuccess() {

        Map<String, String[]> params = request().queryString();

        String callbackUrl = (params.containsKey("callbackUrl")) ? params.get("callbackUrl")[0] : "/";
        String uemail = (params.containsKey("uemail")) ? params.get("uemail")[0].toLowerCase() : null;
        String token = (params.containsKey("token")) ? params.get("token")[0] : null;

        if (uemail != null && !uemail.equals("") && token != null && !uemail.equals("")) {
            response().setCookie("uemail", uemail);
            response().setCookie("token", token);
        }

        //重定向到目的地址
        return redirect(callbackUrl);
    }

    /**
     * 用于登出，清除Cookie内的用户信息和token
     *
     * @return
     */
    public static Result logout() {
        response().setCookie("uemail", "");
        response().setCookie("token", "");
        return redirect("/");
    }


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

    /**
     * 主持会议列表
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result hostMeetings() {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        return ok(hostMeetings.render(user, token));
    }

    /**
     * 观看会议列表
     * @return
     */
    @With(CheckLoginAction.class)
     public static Result watchMeetings(){
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        return ok(watchMeetings.render(user, token));
    }

    /**
     * 在线观看会议
     * @param meetingId
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result liveWatch(Long meetingId){
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        Meeting meeting = Meeting.find.byId(meetingId);
        return ok(liveWatch.render(meeting, user, token));
    }

    /**
     * 在线控制会议
     * @param meetingId
     * @return
     */
    @With(CheckLoginAction.class)
    public static Result liveControl(Long meetingId){
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        Meeting meeting = Meeting.find.byId(meetingId);
        return ok(liveControl.render(meeting, user, token));
    }

}
