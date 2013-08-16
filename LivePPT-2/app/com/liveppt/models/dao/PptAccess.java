/**
 * @param
 * @return
 */
package com.liveppt.models.dao;

import java.util.Date;
import java.util.Map;
import com.avaje.ebean.Expression;
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
        Ppt ppt = Ppt.find.where().eq("fileName",pptReader.fileName).eq("user_id", pptReader.userId).findUnique();
        if (ppt==null) {
            ppt = new Ppt(pptReader);
           // System.out.println("pptname:"+ppt.owner.email);
            ppt.save();
            pptReader.id = ppt.id;;
            System.out.println(pptReader.time);
            PptJson pptJson = genPptJson(pptReader);
            return pptJson;
        } else {
        	//ppt重复 throw 相应exception
        	System.out.println("repeat");
            return null;
        }
    }
    
    /**
     * 产生PptJson
     * @param PprReader
     * @return PptJson
     * last modified Zijing Lee
     */
    static public PptJson genPptJson (PptReader ppt) {
    	PptJson pptJson = new PptJson(ppt.userId,ppt.fileName, ppt.time, ppt.fileSize, ppt.convertStatus, ppt.pageCount);
        return pptJson;
    }
}
