package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.MeetingService;

import play.mvc.*;

/**
 * 会议接口
 * Author 黎伟杰
 */

public class MeetingController extends Controller {
    @Inject
    MeetingService meetingService;

    public Result foundNewMeeting() {
        return TODO;
    }

    public Result deleteMeeting() {
        return TODO;
    }

    public Result quitMeeting() {
        return TODO;
    }

    public Result setMeetingPage() {
        return TODO;
    }

    public static Result joinMeeting() {
        return TODO;
    }

    public static WebSocket<String> viewWebsocket() {

        return null;
    }

}
