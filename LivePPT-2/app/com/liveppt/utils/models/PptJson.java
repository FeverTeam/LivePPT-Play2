
package com.liveppt.utils.models;
import java.util.Date;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
/**
 * @author Zijing Lee2013-8-16
 *
 */
public class PptJson extends ObjectNode{

	public static String KEY_PPT_ID = "id";
	public static String KEY_PPT_USER_ID = "userId";
	public static String KEY_PPT_FILENAME = "fileName";
	public static String KEY_PPT_TIME = "time";
	public static String KEY_PPT_FILESIZE = "fileSize";
	public static String KEY_PTT_CONVERT_STATUS = "convertStatus";
	public static String KEY_PPT_PAGECOUNT = "pageCount";
	
	/**
     * 初始化产生PptJson
     * @param userId,fileName,time,fileSize,convertStatus,pageCount
     * last modified Zijing Lee
     */
    public PptJson(Long userId,String fileName, Date time, Long fileSize, boolean convertStatus, int pageCount){
        super(JsonNodeFactory.instance);
        putUserId(userId);
        putFileName(fileName);
        putTime(time);
        putFileSize(fileSize);
        putConvertStatus(convertStatus);
        putPageCount(pageCount);
    }

    /**
     * 为PptJson加入userId
     * @param userId
     * @return itselt
     * last modified Zijing Lee
     */
    public PptJson putUserId(Long userId){
        this.put(KEY_PPT_USER_ID,userId);
        return this;
    }

    /**
     * 从PptJson取出userId状态信息
     * @param
     * @return  itself
     * last modified Zijing Lee
     */
    public String getUserId(){
        return this.get(KEY_PPT_USER_ID).asText();
    }

    /**
     * 为PptJson加入filename
     * @param fileName
     * @return  itself
     * last modified Zijing Lee
     */
    public PptJson putFileName(String fileName){
        this.put(KEY_PPT_FILENAME,fileName);
        return this;
    }

    /**
     * 从PptJson取出filename状态信息
     * @param
     * @return  itself
     * last modified Zijing Lee
     */
    public String getFileName(){
        return this.get(KEY_PPT_FILENAME).asText();
    }

    /**
     * 为PptJson加入filesize信息
     * @param fileSize
     * @return  itself
     * last modified Zijing Lee
     */
    public PptJson putFileSize(Long fileSize){
        this.put(KEY_PPT_FILESIZE,fileSize);
        return this;
    }

    /**
     * 从PptJson取出filesize状态信息
     * @param
     * @return  itself
     * last modified Zijing Lee
     */
    public String getFileSize(){
        return this.get(KEY_PPT_FILESIZE).asText();
    }
    
    /**
     * 为PptJson加入convertStatus信息
     * @param convertStatus
     * @return  itself
     * last modified Zijing Lee
     */
    public PptJson  putConvertStatus(boolean convertStatus){
        this.put(KEY_PTT_CONVERT_STATUS,convertStatus);
        return this;
    }

    /**
     * 从PptJson取出convertStatus状态信息
     * @param
     * @return  itself
     * last modified Zijing Lee
     */
    public String getConvertStatus(){
        return this.get(KEY_PPT_TIME).asText();
    }
    
    /**
     * 为PptJson加入pagecount信息
     * @param display
     * @return  itself
     * last modified Zijing Lee
     */
    public PptJson putPageCount(int pageCount){
        this.put(KEY_PPT_PAGECOUNT,pageCount);
        return this;
    }

    /**
     * 从PptJson取出pagecount状态信息
     * @param
     * @return  itself
     * last modified Zijing Lee
     */
    public String getPageCount(){
        return this.get(KEY_PPT_PAGECOUNT).asText();
    }
    
    /**
     * 为UserJson加入time信息
     * @param display
     * @return  itself
     * last modified Zijing LEE
     */
    public PptJson putTime(Date time){
        this.put(KEY_PPT_TIME,time.toString());
        return this;
    }

    /**
     * 从PptJson取出time状态信息
     * @param
     * @return  itself
     * last modified Zijing LEE
     */
    public String getTime(){
        return this.get(KEY_PPT_TIME).asText();
    }
}
