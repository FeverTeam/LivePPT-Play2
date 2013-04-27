package com.fever.liveppt.service;

import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;


/**
 * 会议服务
 * @author 梁博文
 *
 */
public interface MeetingService {
	
	/**
	 * 新建会议
	 * @param founder
	 * @param ppt
	 * @param topic
	 */
	public void foundNewMeeting(Long founderId, Long pptId, String topic);
	
	/**
	 * 删除会议
	 * @param meetingId
	 */
	public void deleteMeeting(Long meetingId);

}
