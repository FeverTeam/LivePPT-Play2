package controllers;

import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fever.liveppt.models.User;

/**
 * 有关用户的数据接口
 * @author 梁博文
 *
 */
public class UserController extends Controller {
	public static Result login(){
		ObjectNode result = Json.newObject();
		
		// 获取POST参数
		Map<String, String[]> postData = request().body().asFormUrlEncoded();
		String email = postData.get("email")[0];
		String password = postData.get("password")[0];
		
		User user = User.isPasswordCorrect(email, password);
		if (user!=null){
			//用户验证成功
			result.put("isSuccess", true);
			
			Logger.info(user.email);
			Logger.info(user.displayname);
			
			// 更新session信息
			session("email", user.email);
			session("displayname", user.displayname);
		}else{
			//用户验证失败
			result.put("isSuccess", false);
			result.put("message", "用户名/密码不正确，或未注册。");
		}
		
		return ok(result);
	}
	
	public static Result signup() {
		ObjectNode result = Json.newObject();
		Map<String, String[]> postData = request().body().asFormUrlEncoded();

		if (postData == null) {
			return ok("null");
		}

		// 获取POST参数
		String email = postData.get("email")[0];
		String password = postData.get("password")[0];
		String displayname = postData.get("displayname")[0];

		// 查找是否已经有相同email的用户，若有则返回错误
		if (User.isExistedByEmail(email)) {
			//相同email的用户已存在，拒绝注册
			
			result.put("isSuccess", false);
			result.put("message", "不好意思！<br>相同email地址用户已被注册啦。");
			
			return ok(result);
		} else {
			//相同email的用户未存在，接受注册
			
			// 组装新用户信息
			User user = new User(email, password, displayname);

			// 将用户存入表中
			user.save();

			result.put("isSuccess", true);

			// 更新session信息
			session("email", email);
			session("displayname", displayname);

			return ok(result);
		}
	}
	
	public static Result logout(){
		session().remove("email");
		return Controller.redirect(controllers.routes.Frontend.index());
	}
}
