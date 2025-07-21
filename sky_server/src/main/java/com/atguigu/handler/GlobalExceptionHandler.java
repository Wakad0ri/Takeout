package com.atguigu.handler;

import com.atguigu.constant.MessageConstant;
import com.atguigu.exception.BaseException;
import com.atguigu.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常（这是抓取全部异常的处理）
     * @param （全部异常）BaseException ex
     * @return  Result.error(固定ex.getMessage)
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * 捕获SQLIntegrityConstraintViolationException异常（也就是唯一索引冲突异常）
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
        String message = ex.getMessage();

        log.error("异常信息：{}", message);
        // java.sql.SQLIntegrityConstraintViolationException:
        // 只有这些是ex：
        // Duplicate entry '歪比巴卜' for key 'employee.idx_username' -> Duplicate | entry | '歪比巴卜' | ...

        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = "用户名【" + username + "】已存在";
            return Result.error(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }
}
