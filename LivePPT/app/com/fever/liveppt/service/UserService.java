package com.fever.liveppt.service;

import com.fever.liveppt.utils.JsonResult;

public interface UserService {

	/**
	 * 验证帐号密码
	 * @param email
	 * @param password
	 * @return 返回JsonResult格式的信息
	 */
	public JsonResult isPassworrdCorrect(String email, String password);
	
	/**
	 * 注册新用户
	 * @param email
	 * @param password
	 * @param displayName
	 * @return
	 */
	public JsonResult register(String email, String password, String displayName);

}
