package controllers.app;

import java.util.Map;

import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;
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
		JsonResult resultJson;
		resultJson = checkEmail(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		
		resultJson = checkPassword(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);

		// 获取参数		
		String email = params.get("email")[0];
		String password = params.get("password")[0];

		//验证帐号密码是否匹配
		resultJson = userService.isPassworrdCorrect(email, password);
		
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
		JsonResult resultJson;
		resultJson = checkEmail(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		resultJson = checkPassword(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		resultJson = checkDisplayName(params);
		if (!resultJson.getStatusCode().equals(StatusCode.NONE))
			return ok(resultJson);
		
		// 获取参数		
		String email = params.get("email")[0];
		String password = params.get("password")[0];
		String displayName = params.get("displayName")[0];
		
		//注册用户
		resultJson = userService.register(email, password, displayName);
		
		Logger.info(resultJson.toString());
		
		return ok(resultJson);
	}
		
	/**
	 * 检查email字段
	 * @param params
	 * @return
	 */
	JsonResult checkEmail(Map<String, String[]> params)
	{
		if (!params.containsKey("email")){
			return new JsonResult(false, StatusCode.USER_EMAIL_ERROR, "email字段错误");
		}
		//TODO
		//添加邮件格式检查
	    /*if (! patternNumbers.matcher(params.get("email")[0]).matches())
			return new JsonResult(false, StatusCode.USER_EMAIL_ERROR, "email字段错误");*/
		return new JsonResult(true);
	}
	
	/**
	 * 检查password字段
	 * @param params
	 * @return
	 */
	JsonResult checkPassword(Map<String, String[]> params)
	{
		if (!params.containsKey("password")){
			return new JsonResult(false, StatusCode.USER_PASSWORD_ERROR, "password字段错误");
		}
		return new JsonResult(true);
	}
	
	/**
	 * 检查displayName字段
	 * @param params
	 * @return
	 */
	JsonResult checkDisplayName(Map<String, String[]> params)
	{
		if (!params.containsKey("displayName")){
			return new JsonResult(false, StatusCode.USER_DISPLAYNAME_ERROR, "displayName字段错误");
		}
		return new JsonResult(true);
	}

}
