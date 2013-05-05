package controllers.app;

import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.UserService;
import com.google.inject.Inject;

public class App_UserController extends Controller {
	@Inject
	UserService userService;

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

}
