package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 订单明细信息实体类
 * 作用：用于存储订单中的具体商品信息，包括菜品或套餐的详细信息、数量和金额等
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
@Schema(description = "订单明细信息实体")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // 明细ID，唯一标识
    @Schema(description = "明细ID")
    private Long id;

    // 商品名称
    // 冗余存储订单商品名称，便于历史订单展示
    @Schema(description = "商品名称")
    private String name;

    // 订单ID
    // 关联Orders表的主键，标识该明细所属的订单
    @Schema(description = "订单ID")
    private Long orderId;

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
    // 记录用户购买的份数
    @Schema(description = "商品数量")
    private Integer number;

    // 商品金额
    // 单份商品的金额（非总金额）
    // 使用BigDecimal确保精确计算
    @Schema(description = "商品金额")
    private BigDecimal amount;

    // 商品图片
    // 商品图片的URL或路径，用于订单详情展示
    @Schema(description = "商品图片")
    private String image;
}