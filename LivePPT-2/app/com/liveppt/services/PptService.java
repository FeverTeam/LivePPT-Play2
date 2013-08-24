package com.liveppt.services;

import com.liveppt.utils.exception.meeting.MeetingIdErrorException;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.exception.ppt.PptIdErrorException;
import com.liveppt.utils.exception.ppt.PptPermissionDenyException;
import com.liveppt.utils.models.PptJson;
import com.liveppt.utils.models.PptReader;
import org.codehaus.jackson.JsonNode;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * Date: 13-8-18
 * Time: 下午4:11
 *
 * @author 黎伟杰
 */
public interface PptService {

    public PptReader uploadPpt(PptReader pptReader, File file) throws PptException;

    public void updatePptConvertedStatus(JsonNode messageJson);

    byte[] getPptPage(Long id, Long pptId, Long pageId) throws PptIdErrorException, PptPermissionDenyException;

    byte[] getPptPageFromMeeting(Long id, Long meetingId, Long pageId) throws PptException, MeetingIdErrorException;

    public PptReader getPptInfo(PptReader pptReader) throws PptException;

    public Set<PptReader> getPptListInfo(PptReader pptReader) throws PptException;
}