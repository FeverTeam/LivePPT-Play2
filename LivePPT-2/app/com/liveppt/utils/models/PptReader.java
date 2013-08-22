package com.liveppt.utils.models;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Zijing Lee2013-8-16
 *
 */
public class PptReader {


    //constructors

	 /**
     * 产生PptReader类
     * @param 
     * @param 
     * @return
     * last modified Zijing LEE
     */
    public PptReader(Map<String, String[]> params) {
        this.params = params;
    }

 	public Long id;
	
	public Long userId;
	
	public String fileName;
	
	public Date time;
	
	public Long fileSize;
	
	public boolean convertStatus;
	
	public int pageCount;
	
	public int status;

    public String storekey;

    public Map<String, String[]> params;

    /**
     * 通过params设置Id
     * @return
     * @throws PptIdNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setId()  {
        String id = params.get(PptJson.KEY_PPT_ID)[0];
       // if (id==null) //throw pptNotFound Exeption;
        this.id = Long.valueOf(id);
        return this;
    }

    /**
     * 通过params设置userID
     * @return
     * @throws UserIdNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setUserId()  {
        String userId = params.get(PptJson.KEY_PPT_USER_ID)[0];
        //if (userId==null)// throw  new pptNotFoundException();
        this.userId = Long.valueOf(userId);
        return this;
    }

    /**
     * 通过params设置fileName
     * @return
     * @throws fileNameNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setFileName() {
        String fileName = params.get(PptJson.KEY_PPT_FILENAME)[0];
       // if (fileName==null)// throw  new FileNameNotFoundException();
        this.fileName = fileName;
        return this;
    }

    /**
     * 通过params设置fileSize
     * @return
     * @throws fileSizeNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setFileSize()  {
        String fileSize = params.get(PptJson.KEY_PPT_FILESIZE)[0];
       // if (fileSize==null) //throw  fileSizeNotFoundException();
        this.fileSize = Long.valueOf(fileSize);
        return this;
    }

    /**
     * 通过params设置convertStatus
     * @return
     * @throws convertStatusNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setConvertStatus(Boolean convertStatus){
        //if (convertStatus==null) //throw  new DisplayNotFoundException();
        this.convertStatus =convertStatus;
        return this;
    }

    /**
     * 通过params设置pagecount
     * @return
     * @throws NewDisplayNotFoundException
     * last modified Zijing Lee
     */
    public PptReader setPageCount(){
        String pageCount = params.get(PptJson.KEY_PPT_PAGECOUNT)[0];
       // if (pageCount==null) //throw  new PageCountNotFoundException();
        this.pageCount = Integer.valueOf(pageCount);
        return this;
    }


    /**
     * 通过params设置time
     * @return
     * @throws TimeNotFoundException
     * last modified 黎伟杰
     */
    public PptReader setTime(){
        this.time = new Date();
        return this;
    }


}

