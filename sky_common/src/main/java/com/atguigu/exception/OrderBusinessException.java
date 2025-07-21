package com.atguigu.exception;

/**
 * OrderBusinessException.java
 * 自定义一个订单业务异常类，用于处理订单相关的业务异常情况。
 */
public class OrderBusinessException extends BaseException {

    public OrderBusinessException(String msg) {
        super(msg);
    }
}
