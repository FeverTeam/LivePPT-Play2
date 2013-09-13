package com.fever.liveppt.service;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.exception.user.UserNotExistedException;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.ResultJson;

public interface UserService {

    /**
     * 验证帐号密码
     *
     * @param email
     * @param hashedPassword
     * @return 返回JsonResult格式的信息
     */
    public ResultJson isPassworrdCorrect(String email, String hashedPassword, String seed) throws UserException, CommonException;

    /**
     * 用户注册
     *
     * @param email
     * @param encryptedPassword
     * @param displayName
     * @param seed
     * @return ResultJson
     */
    public ResultJson register(String email, String encryptedPassword, String displayName, String seed) throws InvalidParamsException, CommonException, UserException;

    /**
     * @param userEmail
     * @return
     * @throws UserException
     */
    public boolean isEmailExisted(String userEmail) throws UserException, CommonException;

    /**
     * 根据userEmail来获取对应的User对象
     *
     * @param userEmail
     * @return
     */
    public User getUser(String userEmail) throws UserNotExistedException;

}
