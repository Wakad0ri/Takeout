package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工数据传输对象
 * 作用：用于给前端展示数据
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "员工数据传输对象")
public class EmployeeVO implements Serializable {

    // 员工ID：唯一标识
    @Schema(description = "员工ID")
    private Long id;

    // 登录用户名
    // 必填字段，用于系统登录
    // 创建后不可修改
    @Schema(description = "登录用户名", required = true)
    private String username;

    // 员工姓名
    // 员工的真实姓名，用于显示和称呼
    @Schema(description = "员工姓名", required = true)
    private String name;

    // 手机号码
    // 用于联系和账号找回
    @Schema(description = "手机号码", required = true)
    private String phone;

    // 性别
    // 值含义：
    // - 0: 女
    // - 1: 男
    @Schema(description = "性别")
    private String sex;

    // 身份证号码
    // 用于员工身份识别和验证
    @Schema(description = "身份证号码", required = true)
    private String idNumber;
}
