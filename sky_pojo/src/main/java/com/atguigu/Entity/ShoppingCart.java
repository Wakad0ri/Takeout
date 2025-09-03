package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车信息实体类
 * 作用：用于存储和管理用户的购物车信息，包括所选商品、数量、金额等
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
@Schema(description = "购物车信息实体")
public class ShoppingCart implements Serializable {

    private static final long serialVersionUID = 1L;

    // 购物车项ID，唯一标识
    @Schema(description = "购物车项ID")
    private Long id;

    // 商品名称
    // 冗余存储，便于购物车展示，可能是菜品名称或套餐名称
    @Schema(description = "商品名称")
    private String name;

    // 用户ID
    // 关联User表的主键，标识购物车所属用户
    @Schema(description = "用户ID")
    private Long userId;

    // 菜品ID
    // 关联Dish表的主键，记录所选菜品
    // 注：菜品和套餐只能二选一
    @Schema(description = "菜品ID")
    private Long dishId;

    // 套餐ID
    // 关联Setmeal表的主键，记录所选套餐
    // 注：菜品和套餐只能二选一
    @Schema(description = "套餐ID")
    private Long setmealId;

    // 口味选择
    // 用JSON字符串记录用户的口味偏好选择
    @Schema(description = "口味选择")
    private String dishFlavor;

    // 商品数量
    // 记录用户选择的份数
    @Schema(description = "商品数量")
    private Integer number;

    // 商品金额
    // 单份商品的金额（非总金额）
    // 使用BigDecimal确保精确计算
    @Schema(description = "商品金额")
    private BigDecimal amount;

    // 商品图片
    // 商品图片的URL或路径，用于购物车展示
    @Schema(description = "商品图片")
    private String image;

    // 加入时间
    // 记录商品加入购物车的时间点
    @Schema(description = "加入时间")
    private LocalDateTime createTime;
}
