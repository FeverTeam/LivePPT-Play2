package com.fever.liveppt.utils;

import com.fever.liveppt.exception.common.InternalErrorException;
import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import com.fever.liveppt.exception.user.UserNotExistedException;
import com.fever.liveppt.models.User;
import com.fever.liveppt.service.UserService;
import play.libs.Crypto;
import play.mvc.Http;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: Zijing Lee
 * Date: 13-9-27
 * Time: 上午12:43
 * Description: 关于用户登陆后token的操作，包括token的检验，根据token获取用户，生成token等。
 */
public class TokenAgent {

    public static Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$");

    public static String validateTokenFromHeader(Http.Request request) throws InvalidParamsException, TokenInvalidException {
        if (request == null) {
            throw new InvalidParamsException();
        }

        //从Header中获取参数
        String userEmail = request.getHeader("uemail").toLowerCase();
        String inputToken = request.getHeader("token");
        if (userEmail == null || userEmail.length() == 0 || !TokenAgent.isEmailFormatValid(userEmail) || inputToken == null || inputToken.length() == 0) {
            throw new InvalidParamsException();
        }

        if (isTokenValid(inputToken, userEmail)) {
            //Token有效
            return userEmail;
        } else {
            //Token无效，抛出TokenInvalidException
            throw new TokenInvalidException();
        }
    }

    public static User validateTokenAndGetUser(UserService userService, Http.Request request) throws InvalidParamsException, TokenInvalidException, InternalErrorException, UserNotExistedException {
        if (userService == null) {
            throw new InternalErrorException();
        }
        String userEmail = validateTokenFromHeader(request);
        return userService.getUser(userEmail);
    }

    public static String generateToken(String userEmail) {
        if (userEmail == null || !TokenAgent.isEmailFormatValid(userEmail)) {
            return null;
        }
        return Crypto.sign(userEmail);
    }

    public static boolean isTokenValid(String token, String userEmail) {
        if (token == null || userEmail == null || !TokenAgent.isEmailFormatValid(userEmail)) {
            return false;
        }
        String correctToken = generateToken(userEmail);
        if (correctToken != null && token.equals(correctToken)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmailFormatValid(String email) {
        Matcher m = emailPattern.matcher(email);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }
}
