package com.atguigu.DTO.EmployeeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 员工登录数据传输对象
 * 作用：用于接收员工登录时提交的认证信息，包括用户名和密码
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "员工登录数据传输对象")
public class EmployeeLoginDTO implements Serializable {

    // 登录用户名
    // 必填字段，用于系统登录认证
    // 对应员工信息表中的username字段
    @Schema(description = "登录用户名", required = true)
    private String username;

    // 登录密码
    // 必填字段，用于身份验证
    // 传输时应进行加密处理
    @Schema(description = "登录密码", required = true)
    private String password;
}
