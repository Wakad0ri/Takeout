package com.atguigu.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class OrderTask {

    /**
     * 测试定时任务
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        log.info("task1定时任务执行了: {}", new Date());

    }
}
