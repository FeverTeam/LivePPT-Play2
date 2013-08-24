package com.liveppt.services.impl;

import com.liveppt.models.dao.MeetingAccess;
import com.liveppt.services.MeetingService;
import com.liveppt.utils.exception.meeting.MeetingException;
import com.liveppt.utils.exception.meeting.MeetingPermissionDenyException;
import com.liveppt.utils.models.MeetingReader;

import java.util.List;

/**
 * Date: 13-8-21
 * Time: 上午1:04
 *
 * @author 黎伟杰
 */
public class MeetingServiceImpl implements MeetingService {
    @Override
    public MeetingReader foundNewMeeting(MeetingReader meetingReader) {
        meetingReader = MeetingAccess.foundNewMeeting(meetingReader);
        return  meetingReader;
    }

    @Override
    public MeetingReader deleteMeeting(MeetingReader meetingReader) throws MeetingException {
        MeetingAccess.deleteMeeting(meetingReader) ;
        return meetingReader;
    }

    @Override
    public List<MeetingReader> getMyFoundedMeetings(MeetingReader meetingReader) {
        List<MeetingReader> meetingReaders = MeetingAccess.getMyFoundedMeetings(meetingReader.getUserId());
        return meetingReaders;
    }

    @Override
    public List<MeetingReader> getMyAttendingMeetings(MeetingReader meetingReader) {
        List<MeetingReader> meetingReaders = MeetingAccess.getMyAttendingMeetings(meetingReader.getUserId());
        return meetingReaders;
    }

    @Override
    public MeetingReader getMeetingInfoAsFounder(MeetingReader meetingReader) throws MeetingPermissionDenyException {
        meetingReader = MeetingAccess.getMeetingInfoByFounder(meetingReader);
        return  meetingReader;
    }

    public MeetingReader getMeetingInfoAsAttender(MeetingReader meetingReader)  throws MeetingPermissionDenyException{
        meetingReader = MeetingAccess.getMeetingInfoByAttender(meetingReader,meetingReader.getUserId());
        return  meetingReader;
    }
    @Override
    public MeetingReader joinMeeting(MeetingReader meetingReader) {
        MeetingAccess.joinMeeting(meetingReader.getUserId(),meetingReader.getMeetingId());
        return meetingReader;
    }

    @Override
    public MeetingReader setMeetingPageIndex(MeetingReader meetingReader) throws MeetingPermissionDenyException {
        MeetingAccess.setMeetingPage(meetingReader);
        return meetingReader;
    }

    @Override
    public MeetingReader quitMeeting(MeetingReader meetingReader) {
        MeetingAccess.quitMeeting(meetingReader.getUserId(),meetingReader.getMeetingId());
        return meetingReader;
    }
}
