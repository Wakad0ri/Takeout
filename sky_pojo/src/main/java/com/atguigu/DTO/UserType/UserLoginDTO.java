package com.atguigu.DTO.UserType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录数据传输对象
 * 作用：用于接收C端（小程序端）用户登录时的参数，包括微信登录码
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "用户登录数据传输对象")
public class UserLoginDTO implements Serializable {

    // 微信登录码
    // 小程序端调用wx.login()获取的code
    // 用于后端换取用户openid
    // 必填字段
    @Schema(description = "微信登录码", required = true)
    private String code;
}
