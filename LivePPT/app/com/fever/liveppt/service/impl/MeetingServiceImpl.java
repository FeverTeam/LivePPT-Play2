
package com.fever.liveppt.service.impl;

import com.fever.liveppt.constant.Expiration;
import com.fever.liveppt.exception.meeting.AttendingExistedException;
import com.fever.liveppt.exception.meeting.MeetingNotAttendedException;
import com.fever.liveppt.exception.meeting.MeetingNotExistedException;
import com.fever.liveppt.exception.meeting.MeetingPermissionDenyException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.ppt.PptPageOutOfRangeException;
import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.StatusCode;
import play.cache.Cache;
import play.libs.Json;
import ws.wamplay.controllers.WAMPlayServer;

import java.util.ArrayList;
import java.util.List;

import static com.fever.liveppt.utils.MeetingAgent.genMeetingPageCacheKey;
import static com.fever.liveppt.utils.MeetingAgent.getOrCreatePageTopic;

/**
 * @author
 * @version : v1.00
 * @Description : 会议操作接口实现 ，提供给service层调用
 */
public class MeetingServiceImpl implements MeetingService {

    @Override
    public void deleteMeeting(String userEmail, Long meetingId) throws MeetingPermissionDenyException, MeetingNotExistedException {
        Meeting meeting = Meeting.find.byId(meetingId);

        if (meeting == null) {
            throw new MeetingNotExistedException();
        }
        String founder = meeting.founder.email;
        if (!userEmail.equals(founder)) {
            throw new MeetingPermissionDenyException();
        } else {
            List<Attender> attendings = Attender.find.where().eq("meeting_id", meetingId).findList();
            for (Attender attend : attendings) {
                attend.delete();
            }
            meeting.delete();
        }
    }

    @Override
    public ResultJson quitMeeting(String userEmail, Long meetingId) throws MeetingNotExistedException, MeetingNotAttendedException {
        ResultJson resultJson;

        boolean isAttended = false;
        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            throw new MeetingNotExistedException();
        }

        User user = User.find.where().eq("email", userEmail).findUnique();
        if (user.attendents == null) {
            throw new MeetingNotAttendedException();
        }
        for (Attender attender : user.attendents) {
            if (attender.meeting.id.equals(meetingId)) {
                attender.delete();
                isAttended = true;
                break;
            }
        }
        if (isAttended) {
            resultJson = ResultJson.simpleSuccess();
            return resultJson;
        } else {
            throw new MeetingNotAttendedException();
        }


    }

    @Override
    public ResultJson createMeeting(String userEmail, Long pptId, String topic) throws PptNotExistedException, MeetingPermissionDenyException {
        User founder = User.find.where().eq("email", userEmail).findUnique();
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null) {
            throw new PptNotExistedException();
        }
        if (!ppt.owner.id.equals(founder.id)) {
            throw new MeetingPermissionDenyException();
        }
        //新建发起的会议,并存入数据库
        Meeting meeting = new Meeting(founder, ppt, topic);
        meeting.save();

        return ResultJson.simpleSuccess();
    }

    @Override
    public ResultJson updateMeeting(String userEmail, Long meetingId, Long pptId, String topic) throws MeetingNotExistedException, PptNotExistedException, MeetingPermissionDenyException {
        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            throw new MeetingNotExistedException();
        }
        String founder = meeting.founder.email;
        if (!userEmail.equals(founder)) {
            throw new MeetingPermissionDenyException();
        }
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null) {
            throw new PptNotExistedException();
        }
        meeting.ppt = ppt;
        meeting.topic = topic;
        meeting.save();

        return ResultJson.simpleSuccess();
    }

    @Override
    public List<Meeting> getMyAttendingMeetings(String userEmail) {
        List<Meeting> meetings = new ArrayList<>();
        User user = User.find.where().eq("email", userEmail).findUnique();
        for (Attender attender : user.attendents) {
            meetings.add(attender.meeting);
        }
        return meetings;
    }

    @Override
    public List<Meeting> getMyFoundedMeetings(String userEmail) {
        List<Meeting> meetings = new ArrayList<>();
        User user = User.find.where().eq("email", userEmail).findUnique();
        for (Meeting meeting : user.myFoundedMeeting) {
            meetings.add(meeting);
        }
        return meetings;
    }

    @Override
    public ResultJson getMeetingInfo(Long meetingId) throws MeetingNotExistedException {
        ResultJson resultJson;
        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            throw new MeetingNotExistedException();
        } else {
            resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, meeting.toMeetingJson());
            return resultJson;
        }
    }

    @Override
    public ResultJson joinMeeting(String userEmail, Long meetingId) throws MeetingNotExistedException, AttendingExistedException {
        ResultJson resultJson = null;
        boolean isAttended = false;

        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            throw new MeetingNotExistedException();
        }

        User user = User.find.where().eq("email", userEmail).findUnique();

        List<Attender> attendents = null;
        if (!user.attendents.isEmpty()) {
            attendents = user.attendents;
        }


        if (attendents != null) {
            for (Attender attending : attendents) {
                //已经加入
                if (attending.meeting.id.equals(meeting.id)) {
                    resultJson = ResultJson.simpleSuccess();
                    isAttended = true;
                    break;
                }

            }
        }
        if (!isAttended) {
            Attender newAttending = new Attender(meeting, user);
            newAttending.save();
            resultJson = ResultJson.simpleSuccess();
        }

        return resultJson;
    }

    @Override
    public ResultJson setPage(String userEmail, Long meetingId, Long pageIndex) throws MeetingPermissionDenyException, MeetingNotExistedException, PptPageOutOfRangeException {
        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            throw new MeetingNotExistedException();
        }

        User user = User.find.where().eq("email", userEmail).findUnique();

        if (!meeting.founder.id.equals(user.id)) {
            throw new MeetingPermissionDenyException();
        }

        if (pageIndex < 1 || pageIndex > meeting.ppt.pagecount) {
            throw new PptPageOutOfRangeException();
        }

        //向wamp对应topic发布页码更新
        WAMPlayServer.publish(getOrCreatePageTopic(meetingId), Json.toJson(pageIndex));

        //更新Cache中的页码
        String meetingCacheKey = genMeetingPageCacheKey(meetingId);
        Cache.set(meetingCacheKey, pageIndex, Expiration.DEFAULT_MEETING_PAGE_CACHE_EXPIRATION);
        //Logger.debug("setpage meetingid:" + meetingId + " page:" + pageIndex);

        /*
        //更新数据库中的当前页码
        meeting.currentPageIndex = pageIndex;
        meeting.save();
        */

        return ResultJson.simpleSuccess();
    }

}
