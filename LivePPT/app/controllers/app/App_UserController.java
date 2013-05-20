package controllers.app;

import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
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
		//获取get类型的参数
		Map<String, String[]> params = request().queryString();
		
		//检查必须的参数是否存在
		Set<String> keySet = params.keySet();
		if (keySet==null)
			return ok(new JsonResult(false, "","无字段"));
		if (!keySet.contains("email")){
			return ok(new JsonResult(false,"", "email字段不存在。"));
		}
		if (!keySet.contains("password")){
			return ok(new JsonResult(false,"", "password字段不存在。"));
		}

		// 获取参数		
		String email = params.get("email")[0];
		String password = params.get("password")[0];

		//验证帐号密码是否匹配
		ObjectNode resultJson = userService.isPassworrdCorrect(email, password);
		
		Logger.info(resultJson.toString());

		return ok(resultJson);
	}
	
	/**
	 * 用户注册接口
	 * @return
	 */
	public Result register(){
		Map<String, String[]> params = request().body().asFormUrlEncoded();		

		//检查必须的参数是否存在
		Set<String> keySet = params.keySet();
		if (keySet==null)
			return ok(new JsonResult(false,"","无字段"));
		if (!keySet.contains("email")){
			return ok(new JsonResult(false,"","email字段不存在。"));
		}
		if (!keySet.contains("password")){
			return ok(new JsonResult(false,"", "password字段不存在。"));
		}
		if (!keySet.contains("displayName")){
			return ok(new JsonResult(false,"", "displayName字段不存在。"));
		}
		
		// 获取参数		
		String email = params.get("email")[0];
		String password = params.get("password")[0];
		String displayName = params.get("displayName")[0];
		
		//注册用户
		JsonResult resultJson = userService.register(email, password, displayName);
		
		Logger.info(resultJson.toString());
		
		return ok(resultJson);
	}

}
