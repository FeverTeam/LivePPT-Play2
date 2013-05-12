package controllers.app;

import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.JsonResult;
import com.google.inject.Inject;

public class App_UserController extends Controller {
	@Inject
	UserService userService;

	/**
	 * 用户登录接口
	 * @return
	 */
	public Result login() {
		// 返回json对象
		ObjectNode resultJson;

		// 获取POST参数
		Map<String, String[]> postData = request().body().asFormUrlEncoded();
		String email = postData.get("email")[0];
		String password = postData.get("password")[0];

		resultJson = userService.isPassworrdCorrect(email, password);
		
		Logger.info(Json.stringify(resultJson));

		return ok(resultJson);
	}
	
	/**
	 * 用户注册接口
	 * @return
	 */
	public Result register(){
		// 获取POST参数
		Map<String, String[]> postData = request().body().asFormUrlEncoded();
		String email = postData.get("email")[0];
		String password = postData.get("password")[0];
		String displayName = postData.get("displayName")[0];
		
		JsonResult resultJson = userService.register(email, password, displayName);
		
		Logger.info(resultJson.toString());
		
		return ok(resultJson);
	}

}
