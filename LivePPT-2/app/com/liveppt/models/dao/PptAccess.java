/**
 * @param
 * @return
 */
package com.liveppt.models.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liveppt.models.Attender;
import com.liveppt.models.Meeting;
import com.liveppt.models.Ppt;
import com.liveppt.models.User;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.exception.ppt.PptIdErrorException;
import com.liveppt.utils.exception.ppt.PptPermissionDenyException;
import com.liveppt.utils.models.PptReader;



/**
 * @author Zijing Lee2013-8-16
 *
 */
public class PptAccess {
	/**
     * 上传PPT后新建PPT信息
     * @param
     * @return pptJson
     * last modified Zijing Lee 
     */
    
    static public PptReader create(PptReader pptReader){
    	Ppt ppt = new Ppt(pptReader);
    	ppt.save();
    	return pptReader;
    }
    /**
     * 判断用户是否能查阅
     * @param userId,pptId
     * @return storeKey
     * last modified Zijing Lee
     */
    static public String ifReadByPptId(Long userId, Long pptId)
    {
    	Ppt ppt = Ppt.find.byId(pptId);
    	if(ppt.owner.id == userId)
    	{
    		return ppt.storeKey;
    	}
    	else
    	{   
    		//根据user_id找出该user创建的meeting,检查这些meeting对应的ppt有否指定的ppt
    		List<Meeting> meetingList = Meeting.find.where().eq("user_id", userId).findList();
    		if(meetingList != null)
    		{
    			for(Meeting meeting : meetingList)
    			{
    				if(meeting.ppt.id == pptId)
    					return ppt.storeKey;
    			}
    		}
    		//根据ppt_id找出该ppt对应的meeting,检查直嘀咕user是否这些meeting的参与者
    		meetingList = Meeting.find.where().eq("ppt_id",pptId).findList();
    		for(Meeting meeting : meetingList)
    		{
    			List<Attender> attenderList = meeting.attenders;
    			for(Attender attender :attenderList)
    			{
    				if(userId == attender.user.id)
        			return meeting.ppt.storeKey;
    			}
    		}
    		//都找不到 throw permission deny exception
    		
    	}
		return ppt.storeKey;
    }
    /**
     * 判断用户是否能查阅
     * @param userId,meetingId
     * @return storeKey
     * last modified Zijing Lee
     */
    static public String ifReadByMeetingId(Long userId, Long meetingId)
    {
    	Meeting meeting = Meeting.find.byId(meetingId);
    	if(userId == meeting.founder.id)
    	{
    		return meeting.ppt.storeKey;
    	}
    	else
    	{
    		List<Attender> attenderList = meeting.attenders;
    		for(Attender attender :attenderList)
    		{
    			if(userId == attender.user.id)
    				return meeting.ppt.storeKey;
    		}
    		//throw permission deny exception
    		return meeting.ppt.storeKey;
    	}
    }
    /**
     * 更新PPT转换状态
     * @param storekey,isConverted,pageCount
     * @return PptReader
     * last modified Zijing Lee
     */
    static public PptReader updatePptConvertedStatus(String storekey,boolean isConverted,int pageCount){
		List<Ppt> pptList = Ppt.find.where().eq("storeKey", storekey).findList();
		Ppt ppt = pptList.get(0);
		ppt.isConverted = isConverted;
		ppt.pagecount = pageCount;
		ppt.save();
		PptReader pptReader = new PptReader(ppt);
		return pptReader;
    }

    public static PptReader getPptInfo(PptReader pptReader) throws PptException {
        Ppt ppt = Ppt.find.byId(pptReader.getPptId());
        //若此人非ppt拥有者，拒绝提供信息
        if (pptReader.getUserId().equals( ppt.owner.id)){
            pptReader.setFileName(ppt.fileName);
            pptReader.setFileSize(ppt.fileSize);
            pptReader.setPageCount(ppt.pagecount);
            pptReader.setConvertStatus(ppt.isConverted);
            pptReader.setTime(ppt.time);
        }else {
            throw new PptPermissionDenyException();
        }
        return pptReader;
    }

    public static Set<PptReader> getPptListInfo(PptReader pptReader) {
        Set<PptReader> pptReaders = new HashSet<>();
        User user = User.find.byId(pptReader.getUserId());
        List<Ppt> ppts = user.ppts;
        for (Ppt ppt:ppts){
            pptReader = new PptReader();
            pptReader.setPptId(ppt.id);
            pptReader.setUserId(ppt.owner.id);
            pptReader.setFileName(ppt.fileName);
            pptReader.setFileSize(ppt.fileSize);
            pptReader.setPageCount(ppt.pagecount);
            pptReader.setConvertStatus(ppt.isConverted);
            pptReader.setTime(ppt.time);
            pptReaders.add(pptReader);
        }
        return pptReaders;
    }
}
