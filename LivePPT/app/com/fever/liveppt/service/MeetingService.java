package com.fever.liveppt.service;

import com.fever.liveppt.exception.meeting.AttendingExistedException;
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

    /**
     * 新建会议
     *
     * @param founder
     * @param ppt
     * @param topic
     */
    public JsonResult foundNewMeeting(Long founderId, Long pptId, String topic);
    public ResultJson createMeeting(String userEmail,Long pptId,String topic) throws PptNotExistedException;
    /**
     * 删除会议
     *
     * @param meetingId
     */
    public void deleteMeeting(Long meetingId);
    public void deleteMeeting(String userEmail,Long meetingId) throws MeetingPermissionDenyException, MeetingNotExistedException;
    /**
     * 获取用户所有自己发起的会议
     *
     * @param userId
     */
    public ArrayNode getMyFoundedMeetings(Long userId);
    public List<Meeting> getMyFoundedMeetings(String userEmail);
    /**
     * 获取用户观看的会议
     *
     * @param userId
     */
    public ArrayNode getMyAttendingMeetings(Long userId);
    public List<Meeting> getMyAttendingMeetings(String userEmail);
    /**
     * 获取指定的会议的信息
     *
     * @param meetingId
     * @return
     */
  // public JsonResult getMeetingInfo(Long meetingId) throws MeetingNotExistedException;
    public ResultJson getMeetingInfo(Long meetingId) throws MeetingNotExistedException;
    /**
     * 加入新的会议
     *
     * @param userEmail   用户Id
     * @param meetingId 准备加入的会议号
     * @return [description]
     */
    public JsonResult joinMeeting(Long userId, Long meetingId);
    public ResultJson joinMeeting(String userEmail,Long meetingId) throws MeetingNotExistedException, AttendingExistedException;
    /**
     * 设置会议的直播PPT页码
     *
     * @param meetingId
     * @param pageIndex
     * @return
     */
    public JsonResult setMeetingPageIndex(Long meetingId, Long pageIndex);
    public ResultJson setPage(String userEmail,Long meetingId, Long pageIndex) throws MeetingPermissionDenyException, MeetingNotExistedException, PptPageOutOfRangeException;

    /**
     * 退出会议
     *
     * @param userId
     * @param meetingId
     * @return
     */
    public JsonResult quitMeeting(Long userId, Long meetingId);
    public ResultJson quitMeeting(String userEmail,Long meetingId) throws MeetingNotExistedException;
    /**
     * 修改会议
     * @param userEmail
     * @param meetingId
     * @param pptId
     * @param topic
     * @return
     * @throws MeetingNotExistedException
     * @throws PptNotExistedException
     */
    public ResultJson updateMeeting(String userEmail,Long meetingId,Long pptId,String topic) throws MeetingNotExistedException, PptNotExistedException, MeetingPermissionDenyException;
}
