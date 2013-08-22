/**
 * @param
 * @return
 */
package com.liveppt.models.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.liveppt.models.Attender;
import com.liveppt.models.Meeting;
import com.liveppt.models.Ppt;
import com.liveppt.models.User;
import com.liveppt.utils.models.MeetingReader;



/**
 * @author Zijing Lee2013-8-21
 *
 */
public class MeetingAccess {
	
	/**
     * 新建新会议
     * @param MeetingReader
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
     * @param MeetingReader
     * @return boolean
     * last modified Zijing Lee
     */
	static public boolean deleteMeeting(MeetingReader meetingReader){
		Meeting meeting = Meeting .find.byId(meetingReader.getId());
		if(meeting != null)
		{
			meeting.delete();
			return true;
		}
		else
		{
			return false;
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
    public MeetingReader joinMeeting(Long userId,Long meetingId) {
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
    public MeetingReader quitMeeting(Long userId,Long meetingId) {
		User user = User.find.byId(userId);
		Meeting meeting = Meeting.find.byId(meetingId);
			for (Attender attender : user.attendents) {
				if (attender.meeting.id.equals(meetingId))
				{
					attender.delete();
					break;
				}
			}
		return new MeetingReader(meeting);
        
    }
}
