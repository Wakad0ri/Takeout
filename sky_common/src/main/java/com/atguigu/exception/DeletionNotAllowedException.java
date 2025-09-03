package com.atguigu.exception;


/**
 * DeletionNotAllowedException.java
 * 自定义一个删除出错类，用于在删除数据时，如果存在关联数据，则抛出此异常
 */
public class DeletionNotAllowedException extends BaseException{

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }
}
