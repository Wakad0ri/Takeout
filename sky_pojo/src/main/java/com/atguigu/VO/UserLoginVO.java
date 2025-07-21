package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户登录返回视图对象
 * 作用：用于封装微信用户登录成功后返回给前端的数据，包括用户标识和认证令牌
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Builder：提供流式构建对象的能力
 * - @NoArgsConstructor：生成无参构造函数
 * - @AllArgsConstructor：生成全参构造函数
 * - @Schema：用于生成API文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户登录返回视图对象")
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户ID，唯一标识
    // 用于后续接口调用时识别当前登录用户
    @Schema(description = "用户ID")
    private Long id;

    // 微信OpenID
    // 微信平台提供的用户唯一标识
    // 用于用户身份识别和数据关联
    @Schema(description = "微信OpenID")
    private String openid;

    // JWT令牌
    // 用于后续请求的身份验证
    // 需要在请求头中携带此令牌
    @Schema(description = "JWT令牌")
    private String token;
}
