package com.fever.liveppt.service;

import com.fever.liveppt.utils.JsonResult;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.exception.CommonException;
import com.fever.liveppt.utils.exception.UserException;
import com.fever.liveppt.utils.exception.utils.common.InvalidParamsException;

public interface UserService {

	/**
	 * 验证帐号密码
	 * @param email
	 * @param encryptedPassword
	 * @return 返回JsonResult格式的信息
	 */
	public ResultJson isPassworrdCorrect(String email,  String encryptedPassword,String seed) throws UserException;

    /**
     * 用户注册
     * @param email
     * @param encryptedPassword
     * @param displayName
     * @param seed
     * @return ResultJson
     */
	public ResultJson register(String email, String encryptedPassword, String displayName,String seed) throws InvalidParamsException, CommonException, UserException;

    /**
     *
     * @param email
     * @return
     * @throws UserException
     */
    public ResultJson isEmailExisted(String email) throws UserException;

}
