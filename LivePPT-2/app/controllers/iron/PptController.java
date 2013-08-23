package controllers.iron;

import com.google.inject.Inject;
import com.liveppt.services.PptService;

import com.liveppt.utils.ResultJson;
import com.liveppt.utils.exception.meeting.MeetingException;
import com.liveppt.utils.exception.meeting.MeetingIdErrorException;
import com.liveppt.utils.exception.ppt.PptException;
import com.liveppt.utils.exception.ppt.PptFileErrorException;
import com.liveppt.utils.exception.ppt.PptIdErrorException;
import com.liveppt.utils.exception.ppt.PptPageIndexErrorException;
import com.liveppt.utils.exception.user.UserException;
import com.liveppt.utils.exception.user.UserNoLoginException;
import com.liveppt.utils.models.PptJson;
import com.liveppt.utils.models.PptReader;
import com.liveppt.utils.models.UserJson;
import org.codehaus.jackson.JsonNode;
import play.libs.Json;
import play.mvc.*;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Date: 13-8-18
 * Time: 下午4:05
 *
 * @author 黎伟杰
 */
public class PptController extends Controller{
    @Inject
    PptService pptService;

    public static String KEY_USER_ID = "userId";
    public static String KEY_PPT_ID = "pptId";
    public static String KEY_PPT_FILENAME = "fileName";
    public static String KEY_PPT_TIME = "time";
    public static String KEY_PPT_FILESIZE = "fileSize";
    public static String KEY_PTT_CONVERT_STATUS = "convertStatus";
    public static String KEY_PPT_PAGECOUNT = "pageCount";
    public static String KEY_PPT_STOREKEY = "storeKey";
    public static String KEY_PPT_PAGEINDEX = "pageIndex";
    public static String KEY_MEETING_ID = "meetingId";

    /**
     * 上传ppt
     * @return
     * last modified 黎伟杰
     */
    public Result pptUpload(){

        Map<String, String[]> params = request().body().asFormUrlEncoded();

        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_USER_ID);
            if (s_id==null) throw new UserNoLoginException();
            Long id = Long.valueOf(s_id);
            PptReader pptReader = new PptReader();
            pptReader.setUserId(id);

            //提取文件
            Http.MultipartFormData.FilePart filePart = request().body().asMultipartFormData().getFile("PptFile");
            if (filePart==null) throw new PptFileErrorException();
            File file = filePart.getFile();

            pptReader = pptService.uploadPpt(pptReader,file);

            //组装pptJson
            PptJson pptJson = new PptJson();

            Map<String,String> keyValue = new HashMap<>();
            keyValue.put(KEY_PPT_FILENAME,pptReader.getFileName());
            keyValue.put(KEY_PPT_FILESIZE, String.valueOf(pptReader.getFileSize()));
            keyValue.put(KEY_PPT_TIME, String.valueOf(pptReader.getTime()));
            keyValue.put(KEY_PTT_CONVERT_STATUS, String.valueOf(pptReader.getConvertStatus()));
            keyValue.put(KEY_PPT_ID, String.valueOf(pptReader.getPptId()));

            pptJson.setStringField(keyValue);

            resultJson = new ResultJson(pptJson);
        } catch (PptException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        } catch (UserException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }

        return ok(resultJson);
    }

    /**
     * 更新PPT转换的状态
     *
     * @return
     */
    public Result convertstatus() {
        JsonNode json = Json.parse(request().body().asText());
        JsonNode messageJson = Json.parse(json.findPath("Message")
                .getTextValue());
        pptService.updatePptConvertedStatus(messageJson);
        return ok();
    }

    /**
     *
     * 获取某个PPT某页的JPG图像
     * last modified 黎伟杰
     */
    public Result getPptPageFromPpt() {
        String[] ifModifiedSince = request().headers().get(
                Controller.IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length > 0) {
            return status(NOT_MODIFIED);
        }
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_USER_ID);
            String s_pptId = request().getQueryString(KEY_PPT_ID);
            String s_pageId = request().getQueryString(KEY_PPT_PAGEINDEX);
            if (s_id==null) throw new UserNoLoginException();
            if (s_pptId==null) throw new PptIdErrorException();
            if (s_pageId==null) throw new PptPageIndexErrorException();
            Long id = Long.parseLong(s_id);
            Long pptId = Long.parseLong(s_pptId);
            Long pageId = Long.parseLong(s_pageId);

            //获取图片
            byte[] bytes = pptService.getPptPage(id,pptId, pageId);
            // 设置ContentType为image/jpeg
            response().setContentType("image/jpeg");
            // 设置返回头LastModified
            response().setHeader(Controller.LAST_MODIFIED,
                    "" + new Date().getTime());
            resultJson = new ResultJson(bytes);
        } catch (PptException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        } catch (UserException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }

    /**
     * 根据meetingId获取ppt
     * 获取某个PPT某页的JPG图像
     * last modified 黎伟杰
     */
    public Result getPptPageFromMeeting() {
        String[] ifModifiedSince = request().headers().get(
                Controller.IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && ifModifiedSince.length > 0) {
            return status(NOT_MODIFIED);
        }
        ResultJson resultJson = null;
        try {
            //从session里面得到id信息
            String s_id = ctx().session().get(KEY_USER_ID);
            String s_meetingId = request().getQueryString(KEY_MEETING_ID);
            String s_pageId = request().getQueryString(KEY_PPT_PAGEINDEX);
            if (s_id==null) throw new UserNoLoginException();
            if (s_meetingId==null) throw new MeetingIdErrorException();
            if (s_pageId==null) throw new PptPageIndexErrorException();
            Long id = Long.parseLong(s_id);
            Long meetingId = Long.parseLong(s_meetingId);
            Long pageId = Long.parseLong(s_pageId);

            //获取图片
            byte[] bytes = pptService.getPptPageFromMeeting(id, meetingId, pageId);
            // 设置ContentType为image/jpeg
            response().setContentType("image/jpeg");
            // 设置返回头LastModified
            response().setHeader(Controller.LAST_MODIFIED,
                    "" + new Date().getTime());
            resultJson = new ResultJson(bytes);
        } catch (PptException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        } catch (UserException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        } catch (MeetingException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            resultJson = new ResultJson(e);
        }
        return ok(resultJson);
    }
}
