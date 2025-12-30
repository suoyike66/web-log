package com.suoyike.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author: 蓑衣客
 * @url: www.suoyike.com
 * @date: 2025-12-30 15:43
 * @description: 用户名或者密码为空异常
 **/
public class UsernameOrPasswordNullException extends AuthenticationException {
    public UsernameOrPasswordNullException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        super(msg);
    }
}
