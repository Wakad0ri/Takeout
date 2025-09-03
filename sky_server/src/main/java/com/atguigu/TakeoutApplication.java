package com.atguigu;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement    // 开启事务
@EnableAspectJAutoProxy // 开启AOP
@EnableCaching // 开启Spring Cache缓存功能
@EnableScheduling // 开启定时任务
@Slf4j
public class TakeoutApplication {

    public static void main(String[] args) {
        SpringApplication.run(TakeoutApplication.class, args);
        log.info("启动成功...");
    }
}
