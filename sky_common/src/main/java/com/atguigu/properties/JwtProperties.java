package com.atguigu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用于配置JWT（JSON Web Token）相关参数，分为管理端和用户端两部分：
 *  - 管理端：用于生成和验证管理端用户的JWT令牌
 *  - 用户端：用于生成和验证用户端的JWT令牌
 *  作用：用户认证，身份验证，登录状态管理...
 *  注解：@Component：将这个类注册为Spring Bean，使其可以在Spring容器中管理
 *       ConfigurationProperties：声明这个类为配置属性类，用于读取配置文件中的属性值
 *       Data ：略
 */
@Data
@Component
@ConfigurationProperties(prefix = "sky.jwt")
public class JwtProperties {

    // 管理端密钥，token有效期，token名称（都在yml里声明了）
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    // 用户端密钥，token有效期，token名称（都在yml里声明了）
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}
