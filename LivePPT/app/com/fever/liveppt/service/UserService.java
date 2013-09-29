package com.fever.liveppt.service;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.user.PasswordNotMatchException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.exception.user.UserNotExistedException;
import com.fever.liveppt.models.User;
import com.fever.liveppt.utils.ResultJson;

/**
 * @author
 * @version : v1.00
 * @Description : 用户操作接口 ，提供给controller层调用
 *
 */
public interface UserService {

    /**
     *
     * @param userEmail
     * @param oldPassword
     * @param newPassword
     * @return
     * @throws PasswordNotMatchException
     */
    public ResultJson updatePassword(String userEmail,String oldPassword,String newPassword,String seed) throws PasswordNotMatchException;
    /**
     * 验证帐号密码
     *
     * @param email
     * @param hashedPassword
     * @param seed
     * @return
     * @throws UserException
     * @throws CommonException
     */
    public ResultJson isPassworrdCorrect(String email, String hashedPassword, String seed) throws UserException, CommonException;

    /**
     * 用户注册
     *
     * @param email
     * @param encryptedPassword
     * @param displayName
     * @param seed
     * @return
     * @throws InvalidParamsException
     * @throws CommonException
     * @throws UserException
     */
    public ResultJson register(String email, String encryptedPassword, String displayName, String seed) throws CommonException, UserException;

    /**
     * 用户邮箱是否已存在
     *
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
     * @throws UserNotExistedException
     */
    public User getUser(String userEmail) throws UserNotExistedException;

}
