package com.fever.liveppt.service;

import com.fever.liveppt.exception.meeting.AttendingExistedException;
import com.fever.liveppt.exception.meeting.MeetingNotAttendedException;
import com.fever.liveppt.exception.meeting.MeetingNotExistedException;
import com.fever.liveppt.exception.meeting.MeetingPermissionDenyException;
import com.fever.liveppt.exception.ppt.PptNotExistedException;
import com.fever.liveppt.exception.ppt.PptPageOutOfRangeException;
import com.fever.liveppt.models.Meeting;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.ResultJson;
import org.codehaus.jackson.node.ArrayNode;

import java.util.List;


/**
 * 会议服务
 *
 * @author 梁博文
 */
public interface MeetingService {

    @Deprecated
    public ArrayNode getMyAttendingMeetings(Long userId);

    @Deprecated
    public JsonResult foundNewMeeting(Long founderId, Long pptId, String topic);

    @Deprecated
    public void deleteMeeting(Long meetingId);

    @Deprecated
    public ArrayNode getMyFoundedMeetings(Long userId);

    @Deprecated
    public JsonResult joinMeeting(Long userId, Long meetingId);

    @Deprecated
    public JsonResult setMeetingPageIndex(Long meetingId, Long pageIndex);

    @Deprecated
    public JsonResult quitMeeting(Long userId, Long meetingId);

    /**
     * 发起会议
     *
     * @param userEmail
     * @param pptId
     * @param topic
     * @return
     * @throws PptNotExistedException
     */
    public ResultJson createMeeting(String userEmail, Long pptId, String topic) throws PptNotExistedException, MeetingPermissionDenyException;

    /**
     * 删除会议
     *
     * @param userEmail
     * @param meetingId
     * @throws MeetingPermissionDenyException
     * @throws MeetingNotExistedException
     */
    public void deleteMeeting(String userEmail, Long meetingId) throws MeetingPermissionDenyException, MeetingNotExistedException;

    /**
     * 获取用户所有自己发起的会议
     *
     * @param userEmail
     * @return
     */
    public List<Meeting> getMyFoundedMeetings(String userEmail);

    /**
     * 获取用户观看的所有会议
     *
     * @param userEmail
     * @return
     */
    public List<Meeting> getMyAttendingMeetings(String userEmail);

    /**
     * 获取指定的会议的信息
     *
     * @param meetingId
     * @return
     */
    public ResultJson getMeetingInfo(Long meetingId) throws MeetingNotExistedException;

    /**
     * 加入观看会议
     *
     * @param userEmail
     * @param meetingId
     * @return
     * @throws MeetingNotExistedException
     * @throws AttendingExistedException
     */
    public ResultJson joinMeeting(String userEmail, Long meetingId) throws MeetingNotExistedException, AttendingExistedException;

    /**
     * 设置会议的直播PPT页码
     *
     * @param userEmail
     * @param meetingId
     * @param pageIndex
     * @return
     * @throws MeetingPermissionDenyException
     * @throws MeetingNotExistedException
     * @throws PptPageOutOfRangeException
     */
    public ResultJson setPage(String userEmail, Long meetingId, Long pageIndex) throws MeetingPermissionDenyException, MeetingNotExistedException, PptPageOutOfRangeException;

    /**
     * 退出观看会议
     *
     * @param userEmail
     * @param meetingId
     * @return
     * @throws MeetingNotExistedException
     */
    public ResultJson quitMeeting(String userEmail, Long meetingId) throws MeetingNotExistedException, MeetingNotAttendedException;

    /**
     * 修改会议信息
     *
     * @param userEmail
     * @param meetingId
     * @param pptId
     * @param topic
     * @return
     * @throws MeetingNotExistedException
     * @throws PptNotExistedException
     */
    public ResultJson updateMeeting(String userEmail, Long meetingId, Long pptId, String topic) throws MeetingNotExistedException, PptNotExistedException, MeetingPermissionDenyException;
}
