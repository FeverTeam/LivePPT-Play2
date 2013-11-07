package com.fever.liveppt.utils;

/**
 * Created with IntelliJ IDEA.
 * User: Zijing Lee
 * Date: 13-9-27
 * Time: 上午12:43
 * Description:  异常状态码以及异常返回信息静态变量定义
 */
public class StatusCode {
    //一切正常
    public final static int SUCCESS = 0;
    public final static String SUCCESS_MESSAGE = "success";
    //****************通用***********************
    //缺少参数或参数格式有误
    public final static int INVALID_PARAMS = -101;
    public final static String INVALID_PARAMS_MESSAGE = "invalid params";
    //服务器内部错误
    public final static int INTERNAL_ERROR = -102;
    public final static String INTERNAL_ERROR_MESSAGE = "internal error";
    //Token 无效或过期
    public final static int INVALID_TOKEN = -103;
    public final static String INVALID_TOKEN_MESSAGE = "invalid token";
    //未知错误
    public final static int UNKONWN_ERROR = -999;
    public final static String UNKONWN_ERROR_MESSAGE = "unknown error";
    //****************用户类型错误*****************
    //用户已存在
    public final static int USER_EXISTED = -201;
    public final static String USER_EXISTED_MESSAGE = "same user email existed";
    //该email用户未注册
    public final static int USER_NOT_EXISTED = -202;
    public final static String USER_NOT_EXISTED_MESSAGE = "no such user email existed";
    //用户的账号密码配对失败
    public final static int PASSWORD_NOT_MATCH = -203;
    public final static String PASSWORD_NOT_MATCH_MESSAGE = "user email and password not match";
    //*******************PPT类型错误*****************
    //PPT不存在
    public final static int PPT_NOT_EXISTED = -301;
    public final static String PPT_NOT_EXISTED_MESSAGE = "no such ppt";
    //PPT页码超出范围
    public final static int PPT_PAGE_OUT_OF_RANGE = -302;
    public final static String PPT_PAGE_OUT_OF_RANGE_MESSAGE = "page out of range";
    //PPT未转换
    public final static int PPT_NOT_CONVERTED = -303;
    public final static String PPT_NOT_CONVERTED_MESSAGE = "ppt not converted";
    //PPT文件类型不正确
    // 文件并非PPT或PPTX（ContentType不是"application/vnd.ms-powerpoint"或” application/vnd.openxmlformats-officedocument.presentationml.presentation”），或文件名结尾并非以“.ppt”或“.pptx”结束
    public final static int PPT_FILE_INVALID_TYPE = -304;
    public final static String PPT_FILE_INVALID_TYPE_MESSAGE = "file not ppt or pptx";
    //指定PPT并非用户自己所拥有
    public final static int PPT_NOT_SELF_OWN = -305;
    public final static String PPT_NOT_SELF_OWN_MESSAGE = "ppt not of user own";
    //用户没有权限操作该PPT
    public final static int PPT_NOT_PERMISSION_DENY = -306;
    public final static String PPT_NOT_PERMISSION_DENY_MESSAGE = "not have permission to operate the ppt";
    //*******************MEETING类型错误*****************
    //Meeting不存在
    public final static int MEETING_NOT_EXISTED = -401;
    public final static String MEETING_NOT_EXISTED_MESSAGE = "no such meeting";
    //该用户没有权限进行相关meeting操作
    public final static int MEETING_PERMISSION_DENY = -402;
    public final static String MEETING_PERMISSION_DENY_MESSAGE = "not have permission to operate the meeting";
    //该用户已参与相关会议
    public final static int ATTENDING_EXISTED = -403;
    public final static String ATTENDING_EXISTED_MESSAGE = "user has attended the meeting ";
    //该用户没有参与该会议
    public final static int NOT_ATTENDED = -404;
    public final static String NOT_ATTENDED_MESSAGE = "user has not attended the meeting";
}
