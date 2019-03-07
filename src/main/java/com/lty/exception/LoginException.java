package com.lty.exception;


/**
 * 登录异常
 * @author zhouyongbo
 */
public class LoginException extends  RuntimeException {

    public LoginException(String message) {
        super(message);
    }
}
