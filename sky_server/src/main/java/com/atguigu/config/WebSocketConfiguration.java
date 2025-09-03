package com.atguigu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {

    /**
     * WebSocket配置类，用于注册WebSocket的Bean
     * 创建ServerEndpointExporter对象，该对象会自动注册使用了@ServerEndpoint注解的类
     * @return ServerEndpointExporter
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
