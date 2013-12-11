package controllers;

import com.fever.liveppt.models.Meeting;
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
