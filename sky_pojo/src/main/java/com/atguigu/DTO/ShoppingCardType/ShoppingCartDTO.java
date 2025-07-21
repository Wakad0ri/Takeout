package com.atguigu.DTO.ShoppingCardType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;

/**
 * 购物车数据传输对象
 * 作用：用于接收添加商品到购物车的请求参数，支持菜品和套餐的添加
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "购物车数据传输对象")
public class ShoppingCartDTO implements Serializable {

    // 菜品ID
    // 添加菜品到购物车时使用
    // 注：菜品和套餐只能二选一
    @Schema(description = "菜品ID")
    private Long dishId;

    // 套餐ID
    // 添加套餐到购物车时使用
    // 注：菜品和套餐只能二选一
    @Schema(description = "套餐ID")
    private Long setmealId;

    // 口味选择
    // 添加菜品时可选择的口味偏好
    // 使用JSON字符串记录口味选项
    @Schema(description = "口味选择")
    private String dishFlavor;
}