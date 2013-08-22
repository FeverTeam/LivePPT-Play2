/**
 * @param
 * @return
 */
package com.liveppt.models.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liveppt.models.Ppt;
import com.liveppt.utils.models.PptJson;
import com.liveppt.utils.models.PptReader;



/**
 * @author Zijing Lee2013-8-16
 *
 */
public class PptAccess {
	/**
     * 上传PPT后新建PPT信息
     * @param params
     * @return pptJson
     * last modified Zijing Lee 
     */
    static public PptJson create(Map<String, String[]> params)  {
    	PptReader pptReader = new PptReader(params);
    	//Ppt ppt = null;
    	//PPT内容不存在等的判断应该在存入S3的时候判断，此处只存储PPT信息
        //Ppt ppt = Ppt.find.where().eq("fileName",pptReader.fileName).eq("user_id", pptReader.userId).findUnique();
    	//Ppt ppt = Ppt.find.where().eq("fileName",pptReader.fileName).findUnique();
        pptReader.setUserId().setFileName().setFileSize().setTime().setConvertStatus(false);
        Ppt ppt = new Ppt(pptReader);
        // System.out.println("pptname:"+ppt.owner.email);
        ppt.save();
        pptReader.id = ppt.id;;
        System.out.println(pptReader.time);
        PptJson pptJson = genPptJson(pptReader);
        return pptJson;
    
    }
    /**
     * 产生PptJson
     * @param
     * @return PptJson
     * last modified Zijing Lee
     */
    static public PptJson updatePptConvertedStatus(String storekey,boolean isConverted,int pageCount){
		List<Ppt> pptList = Ppt.find.where().eq("storeKey", storekey).findList();
		Ppt ppt = pptList.get(0);
		ppt.isConverted = true;
		ppt.pagecount = pageCount;
		ppt.save();
		PptJson pptJson = genPptJson(ppt);
		return pptJson;
    }
    /**
     * 产生PptJson
     * @param
     * @return PptJson
     * last modified Zijing Lee
     */
    static public PptJson genPptJson (PptReader ppt) {
    	PptJson pptJson = new PptJson(ppt.userId,ppt.fileName, ppt.time, ppt.fileSize, ppt.convertStatus, ppt.pageCount);
        return pptJson;
    }
    
    /**
     * 产生PptJson
     * @param
     * @return PptJson
     * last modified Zijing Lee
     */
    static public PptJson genPptJson (Ppt ppt) {
    	
    	PptJson pptJson = new PptJson(ppt.owner.id,ppt.fileName, ppt.time, ppt.fileSize, ppt.isConverted, ppt.pagecount);
        return pptJson;
    }
}
