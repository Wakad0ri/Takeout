package com.atguigu.DTO.PasswordType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 密码修改数据传输对象
 * 作用：用于接收员工修改密码时的参数，包括员工ID、旧密码和新密码
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "密码修改数据传输对象")
public class PasswordEditDTO implements Serializable {

    // 员工ID，唯一标识
    // 必填字段，用于指定要修改密码的员工
    @Schema(description = "员工ID", required = true)
    private Long empId;

    // 旧密码
    // 必填字段，用于验证身份
    // 传输时应进行加密处理
    @Schema(description = "旧密码", required = true)
    private String oldPassword;

    // 新密码
    // 必填字段，用于设置新的登录密码
    // 传输时应进行加密处理
    @Schema(description = "新密码", required = true)
    private String newPassword;
}
