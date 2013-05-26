package controllers.app;

import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;


import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;
import com.google.inject.Inject;

public class App_PptController extends Controller {

	@Inject
	PptService pptService;

	/**
	 * 获取用户所有PPT的列表
	 * 
	 * @return
	 */
	public Result getPptList() {
		Map<String, String[]> params = request().queryString();
		
		JsonResult resultJson;
		//检查必须的参数是否存在
		//检查userId
		resultJson = checkUserId(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);

		//获取参数
		Long userId = Long.parseLong(params.get("userId")[0]);
		
		//获取ppt
		resultJson = pptService.getPptList(userId);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}
	
	/**
	 * 获取指定PPT的信息
	 * @param pptId
	 * @return
	 */
	public Result getPptInfo() {
		Map<String, String[]> params = request().queryString();
		JsonResult resultJson;
		resultJson = checkPptId(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		Long pptId = Long.valueOf(params.get("pptId")[0]);
		resultJson = pptService.getPptInfo(pptId);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}

	/**
	 * 获取指定PPT和页码的图片
	 * @param pptId
	 * @param pageIndex
	 * @return
	 */
	public Result getPptPage() {
		
		
		String[] ifModifiedSince = request().headers().get(
				Controller.IF_MODIFIED_SINCE);
		if (ifModifiedSince != null && ifModifiedSince.length > 0) {
			return status(NOT_MODIFIED);
		}
		
		Map<String, String[]> params = request().queryString();
		Long pptId = Long.valueOf(params.get("pptId")[0]);
		Long pageIndex = Long.valueOf(params.get("pageIndex")[0]);
		JsonResult resultJson;
		//检查pptId
		resultJson = checkPptId(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		//检查pageIndex
		resultJson = checkPageIndex(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		
		//TODO
		// getPptPage修改返回类型，添加对pageIndex的容错性
		
		// 设置ContentType为image/jpeg
		response().setContentType("image/jpeg");
		// 设置返回头LastModified
		response().setHeader(Controller.LAST_MODIFIED,
				"" + new Date().getTime());
		
		return ok(pptService.getPptPage(pptId, pageIndex));
	}
	
	//数字匹配器
	Pattern patternNumbers = Pattern.compile("^[-\\+]?[\\d]*$");
	/**
	 * 检查userId字段
	 * @param params
	 * @return
	 */
	JsonResult checkUserId(Map<String, String[]> params)
	{
		if (!params.containsKey("userId")){
			return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
		}
		    
	    if (! patternNumbers.matcher(params.get("userId")[0]).matches())
	    	return new JsonResult(false, StatusCode.USER_ID_ERROR, "userId字段错误");
		return new JsonResult(true);
	}
	
	/**
	 * 检查pptId字段
	 * @param params
	 * @return
	 */
	JsonResult checkPptId(Map<String, String[]> params)
	{
		if (!params.containsKey("pptId")){
			return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
		}
		    
	    if (! patternNumbers.matcher(params.get("pptId")[0]).matches())
	    	return new JsonResult(false, StatusCode.PPT_ID_ERROR, "pptId错误");
		return new JsonResult(true);
	}
	
	/**
	 * 检查pageIndex字段
	 * @param params
	 * @return
	 */
	JsonResult checkPageIndex(Map<String, String[]> params)
	{
		if (!params.containsKey("pageIndex")){
			return new JsonResult(false, StatusCode.PPT_PAGEINDEX_ERROR, "pageIndex字段错误");
		}
		if (! patternNumbers.matcher(params.get("pageIndex")[0]).matches())
			return new JsonResult(false, StatusCode.PPT_PAGEINDEX_ERROR, "pageIndex字段错误");
		return new JsonResult(true);
	} 
}
