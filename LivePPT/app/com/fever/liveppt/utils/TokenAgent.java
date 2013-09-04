package com.fever.liveppt.utils;

import com.fever.liveppt.exception.common.InvalidParamsException;
import com.fever.liveppt.exception.common.TokenInvalidException;
import play.libs.Crypto;
import play.mvc.Http;

public class TokenAgent {

    public static String generateToken(String userEmail) {
        if (userEmail == null || userEmail.length() <= 0) {
            return "";
        }
        return Crypto.sign(userEmail);
    }

    public static String validateTokenFromHeader(Http.Request request) throws InvalidParamsException, TokenInvalidException {
        if (request == null) {
            throw new InvalidParamsException();
        }

        //从Header中获取参数
        String userEmail = request.getHeader("uemail");
        String inputToken = request.getHeader("token");
        if (userEmail == null || userEmail.length() == 0 || inputToken == null || inputToken.length() == 0) {
            throw new InvalidParamsException();
        }

        String validToken = Crypto.sign(userEmail);
        if (inputToken.equals(validToken)) {
            //Token有效
            return userEmail;
        } else {
            //Token无效，抛出TokenInvalidException
            throw new TokenInvalidException();
        }
    }
}
