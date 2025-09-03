package com.atguigu.annotation;

import com.atguigu.enumeration.OperationType;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解，用于标识需要填充的字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Tag(name = "自动填充注解")
public @interface AutoFill {

    // 数据库操作类型：INSERT UPDATE（只涉及到这两个）
    OperationType value();

}
