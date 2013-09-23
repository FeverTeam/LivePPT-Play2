package com.fever.liveppt.service.impl;

import com.fever.liveppt.exception.common.CommonException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.user.PasswordNotMatchException;
import com.fever.liveppt.exception.user.UserException;
import com.fever.liveppt.exception.user.UserExistedException;
import com.fever.liveppt.exception.user.UserNotExistedException;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import com.fever.liveppt.utils.DataJson;
import com.fever.liveppt.utils.ResultJson;
import com.fever.liveppt.utils.StatusCode;
import com.fever.liveppt.utils.TokenAgent;
import play.libs.Crypto;

import java.util.HashMap;
import java.util.Map;


public class UserServiceImpl implements UserService {

    @Override
    public boolean isEmailExisted(String userEmail) throws CommonException, UserException {
        if (!User.isEmailFormatValid(userEmail)) {
            //电邮格式不正确
            throw new InvalidParamsException();
        }
        int userCount = User.find.where().eq("email", userEmail).findRowCount();
        if (userCount > 0) {
            // 相同用户Email已存在
            return true;
        } else {
            //未存在
            return false;
        }
    }

    @Override
    public ResultJson isPassworrdCorrect(String email, String hashedPassword, String seed) throws CommonException, UserException {
        if (email == null || seed == null || !User.isEmailFormatValid(email)) {
            //电邮格式不正确
            throw new InvalidParamsException();
        }

        ResultJson resultJson;
        //解密password
       //  String password = Crypto.decryptAES(hashedPassword, seed);        // 验证用户是否存在
        User user = User.find.where().eq("email", email).findUnique();

        if (user == null) {
            // 用户不存在
            // 封装返回信息,用户不存在
            throw new UserNotExistedException();
        } else {
            // 用户存在
           // hashedPassword = Crypto.sign(hashedPassword,seed.getBytes());
            //以用户密码生成hash以供比对
            String userHashedPassword = Crypto.sign(user.password, seed.getBytes());


            // 验证用户密码
            if (hashedPassword.equals(userHashedPassword)) {
                // 密码验证成功
                //生成token
                String token = TokenAgent.generateToken(email);

                Map<String, String> data = new HashMap();
                data.put("token", token);
                data.put("displayName",user.displayname);
                DataJson dataJson = new DataJson(data);

                // 封装返回信息
                resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, dataJson);
            } else {
                throw new PasswordNotMatchException();
            }
        }
        return resultJson;
    }

    @Override
    public ResultJson register(String email, String encryptedPassword, String displayName, String seed) throws CommonException, UserException {
        if (email == null || encryptedPassword == null || displayName == null || seed == null || !User.isEmailFormatValid(email)) {
            //参数不全或电邮格式不正确
            throw new InvalidParamsException();
        }

        //如果displayName为空，设置其为email
        displayName = (displayName == null || displayName.length() == 0) ? email : displayName;

        String password = "";
        try {
            //解密password
           // encryptedPassword = Crypto.encryptAES(encryptedPassword,seed);
            password = Crypto.decryptAES(encryptedPassword, seed);
        } catch (Exception e) {
            //解密失败
            throw new InvalidParamsException();
        }

        // 查找是否已经有相同email的用户，若有则返回错误
        if (User.isExistedByEmail(email)) {
            //相同email的用户已存在，拒绝注册
            throw new UserExistedException(StatusCode.USER_EXISTED, StatusCode.USER_EXISTED_MESSAGE);
        } else {
            //相同email的用户未存在，接受注册

            // 组装新用户信息
            User user = new User(email, password, displayName);

            // 将用户存入表中
            user.save();

            //生成token
            String token = Crypto.sign(email);

            HashMap<String, String> map = new HashMap<String, String>();
            map.put("token", token);

            //封装json格式的data数据
            DataJson dataJson = new DataJson(map);
            ResultJson resultJson = new ResultJson(StatusCode.SUCCESS, StatusCode.SUCCESS_MESSAGE, dataJson);

            //返回
            return resultJson;
        }
    }

    @Override
    public User getUser(String userEmail) throws UserNotExistedException {
        if (userEmail == null) {
            return null;
        }
        User user = User.find.where().eq("email", userEmail).findUnique();
        if (user != null) {
            return user;
        } else {
            throw new UserNotExistedException();
        }

    }

}
