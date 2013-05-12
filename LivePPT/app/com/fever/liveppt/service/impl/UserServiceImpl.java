package com.fever.liveppt.service.impl;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.JsonResult;

public class UserServiceImpl implements UserService {

	public ObjectNode isPassworrdCorrect(String email, String password) {
		ObjectNode jsonNode = Json.newObject();

		// 验证用户帐号密码
		User user = User.isPasswordCorrect(email, password);
		if (user != null) {
			// 验证成功
			ObjectNode data = Json.newObject();
			data.put("userId", user.id);
			data.put("email", user.email);
			data.put("displayName", user.displayname);

			// 封装返回信息
			jsonNode = JsonResult.genResultJson(true, data);
		} else {
			// 验证失败
			ObjectNode data = Json.newObject();
			data.put("message", "用户名/密码不正确，或未注册。");

			// 封装返回信息
			jsonNode = JsonResult.genResultJson(false, data);
		}
		
		return jsonNode;
	}

}
