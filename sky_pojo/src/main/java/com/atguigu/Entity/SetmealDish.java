package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 套餐菜品关系实体类
 * 作用：用于维护套餐和菜品之间的关联关系，记录套餐中包含的具体菜品信息
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
@Schema(description = "套餐菜品关系实体")
public class SetmealDish implements Serializable {

    private static final long serialVersionUID = 1L;

    // 关系ID，唯一标识
    @Schema(description = "关系ID")
    private Long id;

    // 套餐ID
    // 关联Setmeal表的主键，标识菜品所属的套餐
    @Schema(description = "套餐ID")
    private Long setmealId;

    // 菜品ID
    // 关联Dish表的主键，标识套餐包含的菜品
    @Schema(description = "菜品ID")
    private Long dishId;

    // 菜品名称
    // 冗余存储菜品名称，避免多表查询，提高查询效率
    @Schema(description = "菜品名称")
    private String name;

    // 菜品原价
    // 记录菜品的原始价格，用于价格统计和变更追踪
    // 使用BigDecimal确保精确计算
    @Schema(description = "菜品原价")
    private BigDecimal price;

    // 菜品份数
    // 记录该菜品在套餐中的份数
    @Schema(description = "菜品份数")
    private Integer copies;
}
