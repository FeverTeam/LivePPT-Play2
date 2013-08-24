/**
 * @param
 * @return
 */
package com.liveppt.models.dao;

import java.util.ArrayList;
import java.util.List;

import com.liveppt.models.Attender;
import com.liveppt.models.Meeting;
import com.liveppt.models.Ppt;
import com.liveppt.models.User;
import com.liveppt.utils.exception.meeting.MeetingIdErrorException;
import com.liveppt.utils.exception.meeting.MeetingPermissionDenyException;
import com.liveppt.utils.exception.ppt.PptPermissionDenyException;
import com.liveppt.utils.models.MeetingReader;



/**
 * @author Zijing Lee2013-8-21
 *
 */
public class MeetingAccess {
	
	/**
     * 新建新会议
     * @param meetingReader
     * @return MeetingReader
     * last modified Zijing Lee
     */
	static public MeetingReader foundNewMeeting(MeetingReader meetingReader){
		 Meeting meeting = new Meeting(meetingReader);
		 meeting.save();
		 return meetingReader;		 
	}
	
	/**
     * 删除会议
     * @param meetingReader
     * last modified 黎伟杰
     */
	static public void deleteMeeting(MeetingReader meetingReader) throws MeetingPermissionDenyException, MeetingIdErrorException {
		Meeting meeting = Meeting .find.byId(meetingReader.getMeetingId());
		if(meeting != null)
		{
            if (meeting.founder.id==meetingReader.getUserId())
			    meeting.delete();
			else throw new MeetingPermissionDenyException();
		} else {
            throw new MeetingIdErrorException();
        }
	}
	
	/**
     * 获取用户所建立会议列表
     * @param userId
     * @return List<MeetingReader>
     * last modified Zijing Lee
     */
	static public List<MeetingReader> getMyFoundedMeetings(Long userId) {
		List<MeetingReader> meetingReaderList = new ArrayList<MeetingReader>();
        List<Meeting> meetingList = Meeting.find.where().eq("user_id", userId).findList();
        for(Meeting meeting : meetingList)
        {
        	meetingReaderList.add(new MeetingReader(meeting));
        }
        return meetingReaderList;
    }

	/**
     * 获取用户参与的会议列表
     * @param userId
     * @return List<MeetingReader>
     * last modified Zijing Lee
     */
    static public List<MeetingReader> getMyAttendingMeetings(Long userId) {
    	List<MeetingReader> meetingReaderList = new ArrayList<MeetingReader>();
    	List<Attender> attenderList = Attender.find.where().eq("user_id", userId).findList();
    	Long meetingId;
    	Meeting meeting;
    	for(Attender attending : attenderList)
    	{
    		 meetingId= attending.meeting.id;
    		 meeting = Meeting.find.byId(meetingId);
    		 meetingReaderList.add(new MeetingReader(meeting));	
    	}
    	return meetingReaderList;
    	
    }

    /**
     * 加入会议
     * @param userId，meetingId
     * @return MeetingReader
     * last modified Zijing Lee
     */
    public static MeetingReader joinMeeting(Long userId,Long meetingId) {
		Meeting meeting = Meeting.find.byId(meetingId);
		User user = User.find.byId(userId);
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
		return new MeetingReader(meeting);
	}
    
    /**
     * 退出会议
     * @param userId,meetingId
     * @return MeetingReader
     * last modified Zijing Lee
     */
    public static MeetingReader quitMeeting(Long userId, Long meetingId) {
		User user = User.find.byId(userId);
		Meeting meeting = Meeting.find.byId(meetingId);
        for (Attender attender : user.attendents) {
            if (attender.meeting.id.equals(meetingId))
            {
                attender.delete();
                //TODO delete之后是否需要save？
                break;
            }
        }
		return new MeetingReader(meeting);
        
    }
    /**
     * 得到会议信息
     * @param meetingReader
     * @return MeetingReader
     * last modified Zijing Lee
     * @throws MeetingPermissionDenyException 
     */
    public static MeetingReader getMeetingInfoByFounder(MeetingReader meetingReader) throws MeetingPermissionDenyException{
    	Meeting meeting = Meeting.find.byId(meetingReader.getMeetingId());
    	if(meetingReader.getUserId().equals(meeting.founder.id))
    	{
    		meetingReader.setPptId(meeting.ppt.id);
    		meetingReader.setTopic(meeting.topic);
    		meetingReader.setCurrentPageIndex(meeting.currentPageIndex);
    	}
    	else
    	{
            throw new MeetingPermissionDenyException();
        }
        return meetingReader;
    }
    /**
     * 得到会议信息
     * @param meetingReader
     * @return MeetingReader,userId
     * last modified Zijing Lee
     * @throws MeetingPermissionDenyException 
     */
    public static MeetingReader getMeetingInfoByAttender(MeetingReader meetingReader,Long userId) throws MeetingPermissionDenyException
    {   
    	Meeting meeting = Meeting.find.byId(meetingReader.getMeetingId());
    	List<Attender> attenderList = meeting.attenders;
    	boolean flag = false;
    	for(Attender attending : attenderList)
    	{
    		if(attending.id.equals(userId))
    		{
    			flag = true;
    			break;
    		}
    	}
    	//若此人非Meeting参与者，拒绝提供信息
    	if(flag)
    	{
    		meetingReader.setPptId(meeting.ppt.id);
    		meetingReader.setTopic(meeting.topic);
    		meetingReader.setCurrentPageIndex(meeting.currentPageIndex);
    	}
    	else
    	{
    		throw new MeetingPermissionDenyException();
    	}
    	return meetingReader;
    }
    /**
     * 获取会议信息
     * @param meetingReader
     * @return MeetingReader
     * last modified Zijing Lee
     */
    public static MeetingReader setMeetingPage(MeetingReader meetingReader) throws MeetingPermissionDenyException{
    	Meeting meeting = Meeting.find.byId(meetingReader.getMeetingId());
    	//若此人非Meeting操作者，拒绝操作
    	if(meetingReader.getUserId().equals(meeting.founder.id))
    	{
    	meeting.currentPageIndex = meetingReader.getCurrentPageIndex();
    	meeting.save();
    	}
    	else
    	{
    		throw new MeetingPermissionDenyException();
    	}
    	return meetingReader;
    }
}
