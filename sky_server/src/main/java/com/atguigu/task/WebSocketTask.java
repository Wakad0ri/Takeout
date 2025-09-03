package com.atguigu.task;

import com.atguigu.websocket.WebSocketServer;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Tag(name = "WebSocket定时任务类")
public class WebSocketTask {

    @Autowired
    private WebSocketServer webSocketServer;
//
//    @Scheduled(cron = "0/5 * * * * ?")
//    public void sendMessage() {
//        webSocketServer.sendAll("空你几哇");
//    }
}
