package com.fever.liveppt.service.impl;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;

import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.StatusCode;

public class UserServiceImpl implements UserService {

	public JsonResult isPassworrdCorrect(String email, String password) {
		JsonResult jsonNode;

		// 验证用户是否存在
		User user = User.find.where().eq("email", email).findUnique();
		if (user == null) {
			// 用户不存在
			// 封装返回信息,用户不存在
			jsonNode = new JsonResult(false, StatusCode.USER_NOT_EXISTED, "用户不存在");
		} else {
			// 用户存在
			// 验证用户密码
			if (user.password.equals(password)) {
				ObjectNode data = Json.newObject();
				// 密码验证成功
				data.put("userId", user.id);
				data.put("email", user.email);
				data.put("displayName", user.displayname);

				// 封装返回信息
				jsonNode = new JsonResult(true, data);
			} else {

				// 封装返回信息
				jsonNode = new JsonResult(false, StatusCode.USER_PASSWORD_ERROR, "密码错误");
			}
		}
		return jsonNode;
	}

	@Override
	public JsonResult register(String email, String password, String displayName) {
		// TODO Auto-generated method stub

		// 判断是否已存在相同用户
		if (User.isExistedByEmail(email) == true) {
			// 注册失败，已存在相同email帐号的用户
			return new JsonResult(false, StatusCode.USER_EXISTED)
					.setMessage("已存在相同Email的帐号。");
		} else {
			// 注册新用户
			User user = new User(email, password, displayName);
			user.save();
			return new JsonResult(true).setMessage("注册成功");
		}
	}

}
