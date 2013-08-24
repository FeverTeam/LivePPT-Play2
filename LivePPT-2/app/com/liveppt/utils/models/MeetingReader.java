/**
 * @param
 * @return
 */
package com.liveppt.utils.models;

import java.util.Map;

import com.liveppt.models.Meeting;

/**
 * @author Zijing Lee2013-8-21
 *
 */
public class MeetingReader {

    //TODO 所有getter添加exception
   
	//public Map<String,String[]> params;
	
	Long id;
	
	Long userId;
	
	Long pptId;
	
	String topic;
	
	Long currentPageIndex;
	
	public MeetingReader()
	{
		
	}
	
	public MeetingReader(Meeting meeting)
	{
		this.id = meeting.id;
		this.userId = meeting.founder.id;
		this.pptId = meeting.ppt.id;
		this.topic = meeting.topic;
		this.currentPageIndex = meeting.currentPageIndex;
	}
	 /**
    * 产生MeetingReader类
    * @param 
    * @param 
    * @return
    * last modified Zijing LEE
    */
   public MeetingReader(Long id,Long userId,Long pptId,String topic,Long currentPageIndex) {
       this.id = id;
       this.userId = userId;
       this.pptId = pptId;
       this.topic = topic;
       this.currentPageIndex = currentPageIndex;
   }
   /**
    * 设置Id
    * last modified Zijing Lee
    */
   public MeetingReader setId(Long id) {
	   this.id = id;
	   return this;
   }
   /**
    * 获取Id
    * last modified Zijing Lee
    */
   public Long getId()  {
	   return this.id;
   }
   
   /**
    * 设置UserId
    * last modified Zijing Lee
    */
   public MeetingReader setUserId(Long userId) {
	   this.userId = userId;
	   return this;
   }
   
   /**
    * 获取UserId
    * last modified Zijing Lee
    */
   public Long getUserId() {
	   return this.userId;
   }
   
   /**
    * 设置PptId
    * last modified Zijing Lee
    */
   public MeetingReader setPptId(Long pptId) {
	   this.pptId = pptId;
	   return this;
   }
   
   /**
    * 获取Id
    * last modified Zijing Lee
    */
   public Long getPptId() {
	   return this.pptId;
   }
   
   /**
    * 设置topic
    * last modified Zijing Lee
    */
   public MeetingReader setTopic(String topic) {
	   this.topic = topic;
	   return this;
   }
   
   /**
    * 获取topic
    * last modified Zijing Lee
    */
   public String getTopic() {
	   return this.topic;
   }
   
   /**
    * 设置currentPageIndex
    * last modified Zijing Lee
    */
   public MeetingReader setCurrentPageIndex(Long currentPageIndex) {
	   this.currentPageIndex = currentPageIndex;
	   return this;   
   }
   
   /**
    * 获取currentPageIndex
    * last modified Zijing Lee
    */
   public Long getCurrentPageIndex() {
	   return this.currentPageIndex;
   }
}
