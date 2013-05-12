package controllers.app;

import java.util.Map;

import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import com.fever.liveppt.service.PptService;
import com.fever.liveppt.utils.JsonResult;
import com.google.inject.Inject;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class App_PptController extends Controller {

	@Inject
	PptService pptService;

	public Result getPptList() {
		Map<String, String[]> params = request().body().asFormUrlEncoded();
		Long userId = Long.parseLong(params.get("userId")[0]);
		ObjectNode resultJson;
		ArrayNode pptArrayNode = pptService.getPptList(userId);
		resultJson = JsonResult.genResultJson(true, pptArrayNode);
		Logger.info(Json.stringify(resultJson));
		return ok(resultJson);
	}
}
