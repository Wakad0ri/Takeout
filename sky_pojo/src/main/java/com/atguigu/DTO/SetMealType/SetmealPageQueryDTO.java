package com.atguigu.DTO.SetMealType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 套餐分页查询数据传输对象
 * 作用：用于接收套餐列表的分页查询参数，支持多条件筛选和分页控制
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "套餐分页查询数据传输对象")
public class SetmealPageQueryDTO implements Serializable {

    // 当前页码
    // 从1开始的页码编号
    @Schema(description = "页码", example = "1")
    private Integer page;

    // 每页记录数
    // 指定每页显示的数据条数
    @Schema(description = "每页记录数", example = "10")
    private Integer pageSize;

    // 套餐名称
    // 用于按名称进行模糊查询
    // 可选参数
    @Schema(description = "套餐名称", example = "商务套餐")
    private String name;

    // 分类ID
    // 关联Category表的主键
    // 用于按分类筛选套餐
    // 可选参数
    @Schema(description = "分类ID")
    private Integer categoryId;

    // 套餐状态
    // 值含义：
    // - 0: 禁用，停售状态
    // - 1: 启用，起售状态
    // 可选参数
    @Schema(description = "套餐状态：0禁用 1启用")
    private Integer status;
}
