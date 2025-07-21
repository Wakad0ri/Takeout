package com.atguigu.DTO.DishType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜品分页查询数据传输对象
 * 作用：用于接收菜品分页查询的参数，支持多条件筛选和分页控制
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "菜品分页查询数据传输对象")
public class DishPageQueryDTO implements Serializable {

    // 当前页码
    // 从1开始的页码编号
    @Schema(description = "页码", example = "1")
    private Integer page;

    // 每页记录数
    // 指定每页显示的数据条数
    @Schema(description = "每页记录数", example = "10")
    private Integer pageSize;

    // 菜品名称
    // 用于按名称进行模糊查询
    // 可选参数
    @Schema(description = "菜品名称", example = "宫保鸡丁")
    private String name;

    // 分类ID
    // 关联Category表的主键
    // 用于按分类筛选菜品
    // 可选参数
    @Schema(description = "分类ID")
    private Integer categoryId;

    // 菜品状态
    // 值含义：
    // - 0: 禁用，停售状态
    // - 1: 正常，起售状态
    // 可选参数
    @Schema(description = "菜品状态：0禁用 1正常")
    private Integer status;
}
