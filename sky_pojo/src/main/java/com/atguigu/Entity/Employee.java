package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 员工信息实体类
 * 作用：用于存储和管理系统员工的基本信息，包括身份验证和状态管理
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
@Schema(description = "员工信息实体")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    // 员工ID，唯一标识
    @Schema(description = "员工ID")
    private Long id;

    // 登录用户名
    // 用于系统登录，需要保证唯一性
    @Schema(description = "登录用户名")
    private String username;

    // 员工姓名
    // 员工的真实姓名，用于显示和称呼
    @Schema(description = "员工姓名")
    private String name;

    // 登录密码
    // 用于账号安全认证，存储加密后的密码
    @Schema(description = "登录密码")
    private String password;

    // 手机号码
    // 用于联系和账号找回等功能
    @Schema(description = "手机号码")
    private String phone;

    // 性别
    // 可选值：男/女
    @Schema(description = "性别")
    private String sex;

    // 身份证号码
    // 用于员工身份识别和验证
    @Schema(description = "身份证号码")
    private String idNumber;

    // 账号状态
    // 值含义：
    // - 0: 禁用，暂时无法使用系统
    // - 1: 正常，可以正常登录使用
    @Schema(description = "账号状态")
    private Integer status;

    // 入职时间
    // 系统自动生成，记录员工入职的时间点
    @Schema(description = "入职时间")
    private LocalDateTime createTime;

    // 更新时间
    // 系统自动维护，记录最后一次信息更新的时间点
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人ID
    // 记录创建该员工账号的用户ID，用于追踪操作人
    @Schema(description = "创建人ID")
    private Long createUser;

    // 修改人ID
    // 记录最后一次修改该员工信息的用户ID，用于追踪操作人
    @Schema(description = "修改人ID")
    private Long updateUser;
}
