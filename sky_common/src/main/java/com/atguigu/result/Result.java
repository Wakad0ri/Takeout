package com.atguigu.result;

import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 * 重载：Serializable
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 后端返回结果固定属性
     * code（Integer）：1成功，0失败
     * msg （String） ：返回结果描述
     * data（Object） ：返回数据
     */
    private Integer code;
    private String msg;
    private T data;

    /**
     * 操作成功但不需要返回数据
     * @param <T>
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }

    /**
     * 操作成功，返回数据
     * @param object
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.data = object;
        return result;
    }

    /**
     * 操作失败
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
