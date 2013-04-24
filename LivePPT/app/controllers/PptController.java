package controllers;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.cache.Cached;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Http.RequestBody;
import play.mvc.Http.Session;
import play.mvc.Result;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fever.liveppt.models.Ppt;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.AWSUtils;
import com.google.inject.Inject;

public class PptController extends Controller {
	private final static String FILE_PARAM = "Filedata";
	
	private final static String QUEUE_NAME = "LivePPT-pptId-Bus";

	private final static String TOPIC_ARN = "arn:aws:sns:ap-northeast-1:206956461838:liveppt-sns";
	
	
	@Inject
	PptService pptService;
	
	/**
	 * 用于处理ppt上传的请求
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Result pptUpload() throws UnsupportedEncodingException{
		//取出请求体
		RequestBody body = request().body();
		Session sess = ctx().session();
		
		//提取有效userId
		Long userId = User.genUserIdFromSession(sess);
		if (userId==null){
			Logger.info("无法提取有效id");
			return ok(resultJson(false, "无法提取有效id"));
		}
		
		//将request body转换为MultipartFromData
		MultipartFormData multipartData = body.asMultipartFormData();
		//将MultipartFromData转换为formUrlEncoded用于参数提取
		Map<String, String[]> formData = multipartData.asFormUrlEncoded();
		
		//取出文件部分
		FilePart filePart = multipartData.getFile(FILE_PARAM);
		if (filePart!=null){
			//提取文件、文件名、文件大小
			String title = filePart.getFilename();
			File file = filePart.getFile();
			Long filesize = file.length();
			String title2 = new String(title.getBytes("gbk"), "utf-8");
			Logger.info("not null" + title2+file.length()+"-"+title);
			
			//存入AmazonS3
			AmazonS3 s3 = AWSUtils.genTokyoS3();
			String storeKey = UUID.randomUUID().toString().replaceAll("-", "");
			s3.putObject("pptstore", storeKey, file);
			
			
			//存入文件与用户的所有权关系
			Ppt ownership = new Ppt(userId, title, new Date(), storeKey, filesize);
			ownership.save();
			
	        Logger.debug("StoreKey:"+storeKey);
			
			//向SNS写入PPT的id，并告知win端进行转换
			AmazonSQS sqs = AWSUtils.genTokyoSQS();
			CreateQueueRequest createQueueRequest = new CreateQueueRequest(QUEUE_NAME);
            String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
			sqs.sendMessage(new SendMessageRequest(myQueueUrl,storeKey));
			
			return ok(resultJson(true, null));
		} else {
			//filePart为空
			return ok(resultJson(false, "无法获取文件。"));
		}
	}
	
	/**
	 * 更新PPT转换的状态
	 * @return
	 */
	public Result convertstatus(){
		JsonNode json = Json.parse(request().body().asText());
		JsonNode messageJson = Json.parse(json.findPath("Message").getTextValue());
		pptService.updatePptConvertedStatus(messageJson);
		return ok();
	}
	
	/**
	 * 获取某个PPT某页的JPG图像
	 * @return
	 */
	public Result getPptPage(){
		String params = request().body().asText();
		Logger.info(params+"");
		Long pptId = Long.parseLong(request().getQueryString("pptid"));
		Long pageId = Long.parseLong(request().getQueryString("pageid"));
		response().setContentType("image/jpeg");
		return ok(pptService.getPptPage(pptId, pageId));
	}
	
	/**
	 * 用于组装返回给fineUploader插件的json信息
	 * @param isSuccess 是否成功处理
	 * @param errMessage 错误信息
	 * @return
	 */
	public static ObjectNode resultJson(boolean isSuccess, String errMessage){
		ObjectNode jsonNode = Json.newObject();
		if (isSuccess){
			jsonNode.put("success", true);
		} else {
			jsonNode.put("success", false);
			jsonNode.put("error", errMessage);
			jsonNode.put("preventRetry", true);
		}		
		return jsonNode;
	}
}
