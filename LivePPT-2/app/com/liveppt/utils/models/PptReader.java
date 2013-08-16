/**
 * @param
 * @return
 */
package com.liveppt.utils.models;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author Zijing Lee2013-8-16
 *
 */
public class PptReader {
	public static String KEY_PPT_ID = "id";
	public static String KEY_PPT_USER_ID = "userId";
	public static String KEY_PPT_FILENAME = "fileName";
	public static String KEY_PPT_TIME = "time";
	public static String KEY_PPT_FILESIZE = "fileSize";
	public static String KEY_PTT_CONVERT_STATUS = "convertStatus";
	public static String KEY_PPT_PAGECOUNT = "pageCount";
	
	 /**
     * 产生PptReader类
     * @param 
     * @param 
     * @return
     * last modified Zijing LEE
     */
    public PptReader(Long userId,String fileName, Date time, Long fileSize, boolean convertStatus, int pageCount){
        this.userId = userId;
        this.fileName = fileName;
        this.time = time;
        this.fileSize = fileSize;
        this.convertStatus = convertStatus;
        this.pageCount = pageCount;
        }


    /**
     * 产生PptReader类
     * @param params
     * @return
     * last modified Zijing Lee
     */
    public PptReader(Map<String, String[]> params)  {
        System.out.println("genUserR");
       // DateFormat formatter;
       // Date date;
      //  formatter = new SimpleDateFormat("dd--MMM-yy");
        this.userId = Long.valueOf(params.get(KEY_PPT_USER_ID)[0]);
        this.fileName = params.get(KEY_PPT_FILENAME)[0];
       /* try {
			this.time =  (Date)formatter.parse(params.get(KEY_PPT_TIME)[0]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        this.time = new Date();
        this.fileSize = Long.valueOf(params.get(KEY_PPT_FILESIZE)[0]);
        this.convertStatus = Boolean.valueOf(params.get(KEY_PTT_CONVERT_STATUS)[0]);
        this.pageCount = Integer.valueOf(params.get(KEY_PPT_PAGECOUNT)[0]);
        //TODO Exception 有待考虑
    }
	
	public Long id;
	
	public Long userId;
	
	public String fileName;
	
	public Date time;
	
	public Long fileSize;
	
	public boolean convertStatus;
	
	public int pageCount;
	
	public int status;
}
