package com.fever.liveppt.service.impl;

import com.fever.liveppt.models.Attender;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.MeetingService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import play.Logger;
import play.cache.Cache;

import java.util.List;

public class MeetingServiceImpl implements MeetingService {

    @Override
    public void deleteMeeting(Long meetingId) {
        // TODO Auto-generated method stub
        Meeting meeting = Meeting.find.byId(meetingId);
        meeting.delete();
    }

    @Override
    public JsonResult quitMeeting(Long userId, Long meetingId) {
        // TODO Auto-generated method stub
        JsonResult resultJson = new JsonResult(true);
        User user = User.find.byId(userId);
        Boolean isDeleted = false;
        if (user != null) {
            for (Attender attender : user.attendents) {
                if (attender.meeting.id.equals(meetingId)) {
                    attender.delete();
                    isDeleted = true;
                    break;
                }
            }
        } else {
            resultJson = new JsonResult(false, StatusCode.MEETING_NOT_EXISTED);
        }
        if (isDeleted == false)
            resultJson = new JsonResult(false, StatusCode.MEETING_DELETE_MEETING_FAIL);
        return resultJson;
    }


    @Override
    public JsonResult foundNewMeeting(Long founderId, Long pptId, String topic) {
        // TODO Auto-generated method stub
        User founder = User.find.byId(founderId);
        Ppt ppt = Ppt.find.byId(pptId);
        if (ppt == null)
            return new JsonResult(false, StatusCode.PPT_NOT_EXISTED);

        Meeting meeting = new Meeting();
        meeting.founder = founder;
        meeting.ppt = ppt;
        meeting.topic = topic;
        meeting.save();
        return new JsonResult(true);
    }

    @Override
    public ArrayNode getMyAttendingMeetings(Long userId) {
        // TODO Auto-generated method stub
        ArrayNode resultJson = new ArrayNode(JsonNodeFactory.instance);
        User user = User.find.byId(userId);
        if (user != null) {
            for (Attender attender : user.attendents) {
                resultJson.add(attender.meeting.toJson());
            }
        }
        return resultJson;
    }

    @Override
    public ArrayNode getMyFoundedMeetings(Long userId) {
        // TODO Auto-generated method stub
        ArrayNode resultJson = new ArrayNode(JsonNodeFactory.instance);
        User user = User.find.byId(userId);
        if (user != null) {
            for (Meeting meeting : user.myFoundedMeeting) {
                resultJson.add(meeting.toJson());
            }
        }
        return resultJson;
    }

    @Override
    public JsonResult getMeetingInfo(Long meetingId) {
        // TODO Auto-generated method stub
        JsonResult resultJson;
        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            resultJson = new JsonResult(false, StatusCode.MEETING_NOT_EXISTED, "没有该会议。");
        } else {
            resultJson = new JsonResult(true, StatusCode.SUCCESS, meeting.toJson());
        }
        return resultJson;
    }

    @Override
    public JsonResult setMeetingPageIndex(Long meetingId, Long pageIndex) {
        // TODO Auto-generated method stub
        String cacheKey = meetingId.toString();
        Logger.info(meetingId + "-" + pageIndex);
        Cache.set(cacheKey, pageIndex);
        JsonResult resultJson = new JsonResult(true);
        return resultJson;
    }

    @Override
    public JsonResult joinMeeting(Long userId, Long meetingId) {
        JsonResult resultJson;

        Meeting meeting = Meeting.find.byId(meetingId);
        if (meeting == null) {
            resultJson = new JsonResult(false, StatusCode.MEETING_NOT_EXISTED);
            return resultJson;
        }
        User user = User.find.byId(userId);
        if (user == null) {
            resultJson = new JsonResult(false, StatusCode.MEETING_USER_NOT_EXIST);
            return resultJson;
        }

        List<Attender> attendings = user.attendents;
        boolean isAttended = false;
        for (Attender attending : attendings) {
            if (attending.meeting.id.equals(meeting.id)) {
                isAttended = true;
                break;
            }
        }

        if (!isAttended) {
            Attender newAttending = new Attender(meeting, user);
            newAttending.save();
        }
        resultJson = new JsonResult(true);
        return resultJson;
    }

}
