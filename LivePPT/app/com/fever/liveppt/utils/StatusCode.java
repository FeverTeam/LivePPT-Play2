package com.fever.liveppt.utils;

/**
 * 接口状态码信息
 * 1xxx 用户接口类错误码
 * 2xxx PPT接口类错误码
 * 3xxx会议接口类错误码
 * x1xx 查询类错误
 * x2xx获取类错误
 * x3xx增加类错误
 * x4xx删除类错误
 * x5xx更改类错误
 * 9000操作成功
 *
 * @author 黎伟杰
 */
public class StatusCode {
    //一切正常
    public final static int SUCCESS = 0;

    //****************通用***********************
    //缺少参数或参数格式有误
    public final static int INVALID_PARAMS = 101;
    public final static String INVALID_PARAMS_MESSAGE = "invalid params";
    //服务器内部错误
    public final static int INTERNAL_ERROR = 102;
    //Token 无效或过期
    public final static int INVVALID_TOKEN = 103;
    //未知错误
    public final static int UNKONWN_ERROR = 999;
    //****************用户类型错误*****************
    //用户已存在
    public final static int USER_EXISTED = 201;
    public final static String USER_EXISTED_MESSAGE = "same user email existed";
    //用户密码错误
    public final static int USER_PASSWORD_ERROR = 1100;
    //用户不存在
    public final static int USER_NOT_EXISTED = 1101;
    //用户email字段错误
    public final static int USER_EMAIL_ERROR = 1102;
    //用户Id字段错误
    public final static int USER_ID_ERROR = 1103;
    //用户昵称错误
    public final static int USER_DISPLAYNAME_ERROR = 1103;


    //****************PPT类型错误*****************
    //PPT不存在
    public final static int PPT_NOT_EXISTED = 2100;
    //pptId错误
    public final static int PPT_ID_ERROR = 2101;
    //ppt的pageIndex字段错误
    public final static int PPT_PAGEINDEX_ERROR = 2102;
    //PPT列表为空
    public final static int PPT_LIST_NULL = 2200;

    //****************MEETING类型错误**************
    //meetingId字段错误
    public final static int MEETING_ID_ERROR = 3100;
    //用户不存在
    public final static int MEETING_USER_NOT_EXIST = 3101;
    //meeting的pageIndex字段错误
    public final static int MEETING_PAGEINDEX_ERROR = 3102;
    //根据meetingID查询的meeting不存在
    public final static int MEETING_NOT_EXISTED = 3200;
    //topic字段错误
    public final static int MEETING_TOPIC_ERROR = 3300;
    //删除会议错误
    public final static int MEETING_DELETE_MEETING_FAIL = 3400;
}
