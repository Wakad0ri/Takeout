package com.atguigu.exception;

/**
 * 用户未登录异常
 */
public class UserNotLoginException extends BaseException{

    public UserNotLoginException() {}

    public UserNotLoginException(String message) {
        super(message);
    }

}
