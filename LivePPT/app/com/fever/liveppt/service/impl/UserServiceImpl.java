package com.fever.liveppt.service.impl;

import java.util.List;

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

	@Override
	public JsonResult register(String email, String password, String displayName) {
		// TODO Auto-generated method stub
		
		//判断是否已存在相同用户
		List<User> sameUsers = User.find.where().eq("email", email).findList();
		if (sameUsers.size()!=0){
			//注册失败，已存在相同email帐号的用户
			return JsonResult.genResultJson(false, "以存在相同Email的帐号。", null);
		} else {
			//注册新用户
			User user = new User(email, password, displayName);
			user.save();
			return JsonResult.genResultJson(true, "注册成功", null);
		}
	}

}
