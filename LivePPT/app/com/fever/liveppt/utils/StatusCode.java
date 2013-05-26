package com.fever.liveppt.utils;

/**
 * 接口状态码信息
 * @author 黎伟杰
 *
 */
public class StatusCode {
	//一切正常
	public final static int NONE = 0000;
	//****************用户类型错误*****************
	//用户密码错误
	public final static int USER_PASSWORD_ERROR = 1100;
	//用户不存在
	public final static int USER_NOT_EXISTED = 1101;
	//用户已存在
	public final static int USER_EXISTED = 1300;
	
	//****************PPT类型错误*****************
	//PPT不存在
	public final static int PPT_NOT_EXISTED = 2100;
	//PPT列表为空
	public final static int PPT_LIST_NULL = 2200;
	
	//****************MEETING类型错误**************
	//
	public final static int MEETING_ID_ERROR = 3100;
	//
	public final static int MEETING_PAGEINDEX_ERROR = 3100;
	
	
}
