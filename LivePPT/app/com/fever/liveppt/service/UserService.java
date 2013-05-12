package com.fever.liveppt.service;

import org.codehaus.jackson.node.ObjectNode;

public interface UserService {

	/**
	 * 验证帐号密码
	 * @param email
	 * @param password
	 * @return 返回JsonResult格式的信息
	 */
	public ObjectNode isPassworrdCorrect(String email, String password);

}
