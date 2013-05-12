package controllers.app;

import java.util.Date;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.JsonResult;
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
		// Map<String, String[]> params = request().body().asFormUrlEncoded();
		// Long userId = Long.parseLong(params.get("userId")[0]);
		Long userId = Long.parseLong(request().getQueryString("userId"));
		ObjectNode resultJson;
		ArrayNode pptArrayNode = pptService.getPptList(userId);
		resultJson = JsonResult.genResultJson(true, pptArrayNode);
		Logger.info(Json.stringify(resultJson));
		return ok(resultJson);
	}
	
	/**
	 * 获取指定PPT的信息
	 * @param pptId
	 * @return
	 */
	public Result getPptInfo(Long pptId) {
		JsonResult resultJson = pptService.getPptInfo(pptId);
		Logger.info(resultJson.toString());
		return ok(resultJson);
	}

	public Result getPptPage(Long pptId, Long pageIndex) {
		String[] ifModifiedSince = request().headers().get(
				Controller.IF_MODIFIED_SINCE);
		if (ifModifiedSince != null && ifModifiedSince.length > 0) {
			return status(NOT_MODIFIED);
		}
		// 设置ContentType为image/jpeg
		response().setContentType("image/jpeg");
		// 设置返回头LastModified
		response().setHeader(Controller.LAST_MODIFIED,
				"" + new Date().getTime());
		return ok(pptService.getPptPage(pptId, pageIndex));
	}
}
