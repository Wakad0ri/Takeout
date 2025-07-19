package com.atguigu.DTO.SetMealType;

import lombok.Data;
import java.io.Serializable;

/**
 * 购物车数据
 * 作用：封装购物车数据
 *
 * 重载：Serializable
 * 描述：购物车数据
 */
@Data
public class ShoppingCartDTO implements Serializable {

    // 购物车中的菜品Id
    private Long dishId;

    // 购物车中的套餐Id
    private Long setmealId;

    // 购物车中的菜品口味
    private String dishFlavor;

}