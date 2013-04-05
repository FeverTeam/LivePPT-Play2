package controllers;

import java.util.Map;

import org.codehaus.jackson.node.ObjectNode;

import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class User extends Controller {
	public static Result signup(){
		ObjectNode result = Json.newObject();
		Map<String, String[]> postData = request().body().asFormUrlEncoded();
		
		if (postData==null){
			return ok("null");
		}
		String email = postData.get("email")[0];
		String password = postData.get("password")[0];
		String displayname = postData.get("displayname")[0];
		
		Logger.info(email+password+displayname);
		result.put("isSuccess", true);
		result.put("email", email);
		result.put("password", password);
		result.put("displayname", displayname);
		return ok(result);
	}
}
