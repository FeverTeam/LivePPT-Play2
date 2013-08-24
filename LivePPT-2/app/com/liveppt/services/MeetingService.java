package com.liveppt.services;

import com.liveppt.utils.exception.meeting.MeetingException;
import com.liveppt.utils.exception.meeting.MeetingPermissionDenyException;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.models.MeetingReader;
import com.liveppt.utils.models.PptJson;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Date: 13-8-18
 * Time: 下午4:11
 *
 * @author 黎伟杰
 */
public interface MeetingService {

    //TODO 建立MeetingReader

    /**
     * 新建会议
     */
    public MeetingReader foundNewMeeting(MeetingReader meetingReader);

    /**
     * 删除会议
     */
    public MeetingReader deleteMeeting(MeetingReader meetingReader) throws MeetingException;

    /**
     * 获取用户所有自己发起的会议
     */
    public List<MeetingReader> getMyFoundedMeetings(MeetingReader meetingReader);

    /**
     * 获取用户观看的会议
     */
    public MeetingReader getMyAttendingMeetings(MeetingReader meetingReader);

    /**
     * 获取指定的会议的信息
     */
    public MeetingReader getMeetingInfo(MeetingReader meetingReader);

    /**
     * 加入新的会议
     */
    public MeetingReader joinMeeting(MeetingReader meetingReader);

    /**
     * 设置会议的直播PPT页码
     */
    public MeetingReader setMeetingPageIndex(MeetingReader meetingReader);

    /**
     * 退出会议
     */
    public MeetingReader quitMeeting(MeetingReader meetingReader);
}
