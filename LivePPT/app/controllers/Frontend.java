package controllers;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.With;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import views.html.*;

/**
 * 页面Action
 *
 * @author 梁博文
 */
public class Frontend extends Controller {

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
                   /*
    @With(CheckLoginAction.class)
    public static Result index() {
        User user = CheckLoginAction.getUser(ctx());

        String username = (user == null) ? "" : user.displayname;
        return ok(index.render(username));
    }
    */

    @With(CheckLoginAction.class)
    public static Result login() {
        return ok(login.render());
    }

    @With(CheckLoginAction.class)
    public static Result signup() {
        return ok(signup.render());
    }

    @With(CheckLoginAction.class)
    public static Result myppt() {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);
        return ok(myppt.render(user, token));
    }

    @With(CheckLoginAction.class)
    public static Result mymeeting() {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        List<Meeting> myFoundedMeetingList = user.myFoundedMeeting;
        List<Meeting> myAttendingMeetingList = new LinkedList<Meeting>();
        List<Attender> attendents = user.attendents;
        for (Attender attendding : attendents) {
            myAttendingMeetingList.add(attendding.meeting);
        }
        return ok(mymeeting.render(user, myFoundedMeetingList, myAttendingMeetingList));
    }

    @With(CheckLoginAction.class)
    public static Result pptListForMeeting() {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        List<Ppt> ppts = user.ppts;
        List<Ppt> converted = new ArrayList<Ppt>();
        for (Ppt ppt : ppts) {
            if (ppt.isConverted) {
                converted.add(ppt);
            }
        }
        return ok(pptListForMeeting.render(converted));
    }

    public static Result foundNewMeeting(Long pptId) {
        Ppt ppt = Ppt.find.byId(pptId);
        return ok(foundNewMeeting.render(ppt));
    }

    @With(CheckLoginAction.class)
    public static Result pptplainshow(Long pptid) {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        Ppt ppt = Ppt.find.byId(pptid);
        return ok(pptplainshow.render(ppt, user, token));
    }

    @With(CheckLoginAction.class)
    public static Result controlMeeting(Long meetingId) {
        User user = (User) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_USER);
        String token = (String) ctx().args.get(CheckLoginAction.KEY_CTX_ARG_TOKEN);

        Meeting meeting = Meeting.find.byId(meetingId);
        return ok(controlMeeting.render(meeting, user, token));
    }

    public static Result joinMeeting() {
        return ok(joinMeeting.render());
    }

    @With(CheckLoginAction.class)
    public static Result viewMeeting(Long meetingId) {
        User user = CheckLoginAction.getUser(ctx());
        String username = (user == null) ? "" : user.displayname;

        Meeting meeting = Meeting.find.byId(meetingId);
        return ok(viewMeeting.render(meeting, username));
    }

//    @With(CheckLoginAction.class)
//    public static Result appDownload() {
//        User user = CheckLoginAction.getUser(ctx());
//        String username = (user == null) ? "" : user.displayname;
//
//        return ok(appDownload.render(username));
//    }

//    @With(CheckLoginAction.class)
//    public static Result aboutUs() {
//        User user = CheckLoginAction.getUser(ctx());
//        String username = (user == null) ? "" : user.displayname;
//        return ok(aboutUs.render(username));
//    }

    public static Result msg() {
        return ok(msg.render());
    }

    public static Result wampPubSubTest() {
        return ok(wampPubSubTest.render());
    }

    public static Result testLayout() {
        return ok(testLayout.render("test"));
    }
}