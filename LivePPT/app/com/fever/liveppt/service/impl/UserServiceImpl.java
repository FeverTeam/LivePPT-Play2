package com.fever.liveppt.service.impl;

import com.fever.liveppt.utils.*;
import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import static play.api.libs.Crypto.decryptAES;

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
    public  ResultJson register(String email,String password,String displayName,String seed)
    {

        //如果displayName为空，设置其为email
        if(displayName == null)
        {
            displayName = email;
        }
        //解密password
       // password = decryptAES(password,seed);
        // 查找是否已经有相同email的用户，若有则返回错误
        if (User.isExistedByEmail(email)) {
            //相同email的用户已存在，拒绝注册

            JsonResult jsonResult = new JsonResult(false,StatusCode.USER_EXISTED,null,"User Existed");
            ResultJson resultJson = new ResultJson(StatusCode.USER_EXISTED,null,"用户邮件已存在") ;
           //TODO  改为throw UserExistedException
            return resultJson;
        }
        else if(User.isEmailValid(email) == false)
        {
            //电邮格式不正确
            //throw InvalidParamsException
            JsonResult jsonResult = new JsonResult(false,StatusCode.USER_EXISTED,null,"电邮格式不正确。")  ;
            ResultJson resultJson = new ResultJson(StatusCode.INVALID_PARAMS,null,"电邮格式不正确。") ;
            //TODO  改为throw InvailidParmException
            return resultJson;
        }
        else {
            //相同email的用户未存在，接受注册

            // 组装新用户信息
            User user = new User(email, password, displayName);

            // 将用户存入表中
            user.save();

            //生成token
            String token = Md5Util.getMd5(email);

            Map<String,String> map = new HashMap<String,String>();
            map.put("email",email);
            map.put("displayName",displayName);
            map.put("password",password);
            map.put("token",token);

            //封装json格式的data数据
            DataJson dataJson = new DataJson();
            dataJson.setStringField(map);
            JsonResult results = new JsonResult(true,StatusCode.NONE,dataJson,"Sign up succefully")   ;
            ResultJson resultJson = new ResultJson(StatusCode.NONE,dataJson,"Sign up succefully")   ;

            System.out.println(results.getMessage());
            System.out.println(results.getData().get("token")) ;
            // 更新session信息
            return resultJson;
        }
    }

}
