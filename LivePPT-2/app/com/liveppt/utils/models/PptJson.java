
package com.liveppt.utils.models;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
/**
 * @author Zijing Lee,黎伟杰
 *
 */
public class PptJson extends ObjectNode{
	
	/**
     * 初始化产生PptJson
     * last modified 黎伟杰
     */
    public PptJson(){
        super(JsonNodeFactory.instance);
    }

    public PptJson setStringField(Map<String, String> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }

    public PptJson setJsonField(Map<String, JsonNode> keyValue){
        for (String key:keyValue.keySet()){
            put(key,keyValue.get(key));
        }
        return this;
    }
//    /**
//     * 为PptJson加入userId
//     * @param userId
//     * @return itselt
//     * last modified Zijing Lee
//     */
//    public PptJson putUserId(Long userId){
//        this.put(KEY_PPT_USER_ID,userId);
//        return this;
//    }
//
//    /**
//     * 从PptJson取出userId状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public String getUserId(){
//        return this.get(KEY_PPT_USER_ID).asText();
//    }
//
//    /**
//     * 为PptJson加入filename
//     * @param fileName
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public PptJson putFileName(String fileName){
//        this.put(KEY_PPT_FILENAME,fileName);
//        return this;
//    }
//
//    /**
//     * 从PptJson取出filename状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public String getFileName(){
//        return this.get(KEY_PPT_FILENAME).asText();
//    }
//
//    /**
//     * 为PptJson加入filesize信息
//     * @param fileSize
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public PptJson putFileSize(Long fileSize){
//        this.put(KEY_PPT_FILESIZE,fileSize);
//        return this;
//    }
//
//    /**
//     * 从PptJson取出filesize状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public String getFileSize(){
//        return this.get(KEY_PPT_FILESIZE).asText();
//    }
//
//    /**
//     * 为PptJson加入convertStatus信息
//     * @param convertStatus
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public PptJson  putConvertStatus(boolean convertStatus){
//        this.put(KEY_PTT_CONVERT_STATUS,convertStatus);
//        return this;
//    }
//
//    /**
//     * 从PptJson取出convertStatus状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public String getConvertStatus(){
//        return this.get(KEY_PPT_TIME).asText();
//    }
//
//    /**
//     * 为PptJson加入pagecount信息
//     * @param display
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public PptJson putPageCount(int pageCount){
//        this.put(KEY_PPT_PAGECOUNT,pageCount);
//        return this;
//    }
//
//    /**
//     * 从PptJson取出pagecount状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing Lee
//     */
//    public String getPageCount(){
//        return this.get(KEY_PPT_PAGECOUNT).asText();
//    }
//
//    /**
//     * 为UserJson加入time信息
//     * @param display
//     * @return  itself
//     * last modified Zijing LEE
//     */
//    public PptJson putTime(Date time){
//        this.put(KEY_PPT_TIME,time.toString());
//        return this;
//    }
//
//    /**
//     * 从PptJson取出time状态信息
//     * @param
//     * @return  itself
//     * last modified Zijing LEE
//     */
//    public String getTime(){
//        return this.get(KEY_PPT_TIME).asText();
//    }
}
