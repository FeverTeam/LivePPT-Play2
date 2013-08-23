package com.liveppt.services;

import com.liveppt.utils.exception.ppt.PptException;
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

    byte[] getPptPage(Long id, Long pptId, Long pageId);

    byte[] getPptPageFromMeeting(Long id, Long meetingId, Long pageId);

    public Set<PptReader> getPptList(PptReader pptReader, File file) throws PptException;
}
