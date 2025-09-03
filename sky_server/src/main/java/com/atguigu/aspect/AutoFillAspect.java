package com.atguigu.aspect;

import com.atguigu.annotation.AutoFill;
import com.atguigu.context.BaseContext;
import com.atguigu.enumeration.OperationType;
import com.atguigu.constant.AutoFillConstant;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现自动填充
 */
@Aspect
@Component
@Slf4j
@Tag(name = "自动填充切面")
public class AutoFillAspect {

    /**
     * 切入点
     */
    @Pointcut("execution(* com.atguigu.mapper.*.*(..)) && @annotation(com.atguigu.annotation.AutoFill)")
    public void autoFillPointCut(){}

    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){
        log.info("开始进行公共字段的自动填充...");
        // 获取到当前被拦截的方法上的数据库操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature(); // 获得方法签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);    // 获取方法上的注解对象

        // 获取到当前被拦截的方法的参数——实体对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return;
        }
        Object entity = args[0];

        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentUserId = BaseContext.getCurrentId();

        // 根据当前不同的操作类型，为对应的属性通过反射来赋值
        if(autoFill.value() == OperationType.INSERT){
            // 为插入操作的4个公共字段赋值
            try {
                // 但是在common中已经定义“setUpdateTime”字符串为AutoFillConstant.SET_UPDATE_TIME 所以可以改为这样=
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对应的属性赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentUserId);
                setUpdateUser.invoke(entity, currentUserId);

            }   catch (Exception e) {
                e.printStackTrace();
            }

        } else if(autoFill.value() == OperationType.UPDATE){
            // 为更新操作的2个公共字段赋值
            try {
                // 原先是.getDeclaredMethod("setUpdateTime", LocalDateTime.class)，
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对应的属性赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentUserId);

            }   catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
