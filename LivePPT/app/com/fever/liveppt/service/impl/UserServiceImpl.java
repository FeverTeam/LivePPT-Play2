package com.fever.liveppt.service.impl;

import com.fever.liveppt.utils.*;
import com.fever.liveppt.utils.exception.CommonException;
import com.fever.liveppt.utils.exception.UserException;
import com.fever.liveppt.utils.exception.user.UserExcistedException;
import com.fever.liveppt.utils.exception.utils.common.InvalidParamsException;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Crypto;
import play.libs.Json;

import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;

import java.util.HashMap;
import java.util.Map;


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
    public ResultJson register(String email, String encryptedPassword, String displayName, String seed) throws CommonException, UserException {

        //如果displayName为空，设置其为email
        displayName = (displayName == null || displayName.length()==0) ? email : displayName;


        //解密password
        String password = Crypto.decryptAES(encryptedPassword, seed);

        // 查找是否已经有相同email的用户，若有则返回错误
        if (User.isExistedByEmail(email)) {
            //相同email的用户已存在，拒绝注册
            throw new UserExcistedException(StatusCode.USER_EXISTED, StatusCode.USER_EXISTED_MESSAGE);
        } else if (User.isEmailValid(email) == false) {
            //电邮格式不正确
            throw new InvalidParamsException();
        } else {
            //相同email的用户未存在，接受注册

            // 组装新用户信息
            User user = new User(email, password, displayName);

            // 将用户存入表中
            user.save();

            //生成token
            String token = Crypto.sign(email);

            Map<String, String> map = new HashMap<String, String>();
            map.put("token", token);

            //封装json格式的data数据
            DataJson dataJson = new DataJson(map);
            ResultJson resultJson = new ResultJson(StatusCode.SUCCESS, dataJson, "注册成功");

            // 更新session信息
            return resultJson;
        }
    }

}
