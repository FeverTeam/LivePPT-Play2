package com.fever.liveppt.service;

import com.fever.liveppt.utils.JsonResult;
import org.codehaus.jackson.node.ArrayNode;


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

    /**
     * 删除会议
     *
     * @param meetingId
     */
    public void deleteMeeting(Long meetingId);

    /**
     * 获取用户所有自己发起的会议
     *
     * @param userId
     */
    public ArrayNode getMyFoundedMeetings(Long userId);

    /**
     * 获取用户观看的会议
     *
     * @param userId
     */
    public ArrayNode getMyAttendingMeetings(Long userId);

    /**
     * 获取指定的会议的信息
     *
     * @param meetingId
     * @return
     */
    public JsonResult getMeetingInfo(Long meetingId);

    /**
     * 加入新的会议
     *
     * @param userId    用户Id
     * @param meetingId 准备加入的会议号
     * @return [description]
     */
    public JsonResult joinMeeting(Long userId, Long meetingId);

    /**
     * 设置会议的直播PPT页码
     *
     * @param meetingId
     * @param pageIndex
     * @return
     */
    public JsonResult setMeetingPageIndex(Long meetingId, Long pageIndex);

    /**
     * 退出会议
     *
     * @param userId
     * @param meetingId
     * @return
     */
    public JsonResult quitMeeting(Long userId, Long meetingId);
}
