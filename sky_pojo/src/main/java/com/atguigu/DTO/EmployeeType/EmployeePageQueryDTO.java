package com.atguigu.DTO.EmployeeType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 员工分页查询数据传输对象
 * 作用：用于接收员工列表的分页查询参数，支持按姓名搜索
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "员工分页查询数据传输对象")
public class EmployeePageQueryDTO implements Serializable {

    // 员工姓名
    // 用于按姓名进行模糊查询
    // 可选参数
    @Schema(description = "员工姓名", example = "张三")
    private String name;

    // 当前页码
    // 从1开始的页码编号
    @Schema(description = "页码", example = "1")
    private int page;

    // 每页记录数
    // 指定每页显示的数据条数
    @Schema(description = "每页记录数", example = "10")
    private int pageSize;
}
