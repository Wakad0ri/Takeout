package com.atguigu.DTO.DataType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类分页查询数据传输对象
 * 作用：用于接收前端的分类分页查询参数，支持按名称和类型进行过滤
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "分类分页查询数据传输对象")
public class CategoryPageQueryDTO implements Serializable {

    // 当前页码
    // 从1开始的页码编号
    @Schema(description = "页码", example = "1")
    private Integer page;

    // 每页记录数
    // 指定每页显示的数据条数
    @Schema(description = "每页记录数", example = "10")
    private Integer pageSize;

    // 分类名称
    // 用于按名称进行模糊查询
    // 可选参数
    @Schema(description = "分类名称", example = "川菜")
    private String name;

    // 分类类型
    // 值含义：
    // - 1: 菜品分类
    // - 2: 套餐分类
    // 可选参数
    @Schema(description = "分类类型：1菜品分类 2套餐分类", example = "1")
    private Integer type;
}
