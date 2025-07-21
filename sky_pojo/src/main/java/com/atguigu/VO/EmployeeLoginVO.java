package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 员工登录返回视图对象
 * 作用：用于封装员工登录成功后返回给前端的数据，包括用户信息和认证令牌
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Builder：提供流式构建对象的能力，可以链式调用设置属性
 * - @NoArgsConstructor：生成无参构造函数
 * - @AllArgsConstructor：生成全参构造函数
 * - @Schema：用于生成API文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "员工登录返回视图对象")
public class EmployeeLoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 员工ID，唯一标识
    // 用于后续接口调用时识别当前登录员工
    @Schema(description = "员工ID")
    private Long id;

    // 登录用户名
    // 员工的登录账号，用于展示和后续登录
    @Schema(description = "登录用户名")
    private String userName;

    // 员工姓名
    // 员工的真实姓名，用于界面显示
    @Schema(description = "员工姓名")
    private String name;

    // JWT令牌
    // 用于后续请求的身份验证
    // 需要在请求头中携带此令牌
    @Schema(description = "JWT令牌")
    private String token;
}
