package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户信息实体类
 * 作用：用于存储和管理微信用户的基本信息，支持用户认证和个人信息管理
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
@Schema(description = "用户信息实体")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    // 用户ID，唯一标识
    @Schema(description = "用户ID")
    private Long id;

    // 微信OpenID
    // 微信平台提供的用户唯一标识，用于用户身份识别
    @Schema(description = "微信OpenID")
    private String openid;

    // 用户姓名
    // 用户的真实姓名，用于显示和称呼
    @Schema(description = "用户姓名")
    private String name;

    // 手机号码
    // 用于联系和账号验证
    @Schema(description = "手机号码")
    private String phone;

    // 性别
    // 值含义：
    // - 0: 女
    // - 1: 男
    @Schema(description = "性别")
    private String sex;

    // 身份证号码
    // 用于实名认证和身份验证
    @Schema(description = "身份证号码")
    private String idNumber;

    // 用户头像
    // 存储头像的URL地址，用于界面展示
    @Schema(description = "头像地址")
    private String avatar;

    // 注册时间
    // 记录用户首次使用小程序的时间点
    @Schema(description = "注册时间")
    private LocalDateTime createTime;
}
