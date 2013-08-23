package com.liveppt.utils.models;
import com.liveppt.models.Ppt;
import com.liveppt.utils.exception.ppt.PptIdErrorException;

import java.util.Date;

/**
 * @author Zijing Lee,黎伟杰
 *
 */
public class PptReader {


    //constructors

	 /**
     * 产生PptReader类
     * @param 
     * @param 
     * @return
     * last modified 黎伟杰
     */
    public PptReader() {
    }
    
    public PptReader(Ppt ppt)
    {
    	this.id = ppt.id;
    	this.userId = ppt.owner.id;
    	this.fileName = ppt.fileName;
    	this.fileSize = ppt.fileSize;
    	this.pageCount = ppt.pagecount;
    	this.storekey = ppt.storeKey;
    	this.time = ppt.time;
    }

 	private Long id;

    private Long userId;

    private String fileName;

    private Date time;

    private Long fileSize;

    private Boolean convertStatus;

    private int pageCount;

    private String storekey;

    //TODO getter的抛出异常和异常类的建立

    /**
     * last modified 黎伟杰
     */
    public PptReader setPptId(Long pptId)  {
        this.id = pptId;
        return this;
    }

    /**
     * @throws PptIdErrorException
     * last modified 黎伟杰
     */
    public Long getPptId() throws PptIdErrorException {
        if (id==null) throw new PptIdErrorException();
        return id;
    }

    /**
     * last modified 黎伟杰
     */
    public PptReader setUserId(Long userId)  {
        this.userId = userId;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public Long getUserId()  {
        //if (userId==null) throw new UserId
        return userId;
    }

    /**
     * last modified 黎伟杰
     */
    public PptReader setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * last modified 黎伟杰
     */
    public PptReader setFileSize(Long fileSize)  {
        this.fileSize = fileSize;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public Long getFileSize()  {
        return fileSize;
    }

    /**
     * last modified 黎伟杰
     */
    public PptReader setConvertStatus(Boolean convertStatus){
        //if (convertStatus==null) //throw  new DisplayNotFoundException();
        this.convertStatus =convertStatus;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public Boolean getConvertStatus(){
        return convertStatus;
    }

    /**
     * last modified 黎伟杰
     * @param pageCount
     */
    public PptReader setPageCount(int pageCount){
        this.pageCount = pageCount;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public int getPageCount(){
        return this.pageCount;
    }


    /**
     * last modified 黎伟杰
     * @param time
     */
    public PptReader setTime(Date time){
        this.time = time;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public Date getTime(){
        return time;
    }

    /**
     * last modified 黎伟杰
     */
    public PptReader setStoreKey(String storeKey){
        this.storekey = storeKey;
        return this;
    }

    /**
     * last modified 黎伟杰
     */
    public Date getStoreKey(){
        return this.getStoreKey();
    }

}

