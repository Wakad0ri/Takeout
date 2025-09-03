package com.atguigu.service;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 标准化所有购物车项的口味数据
     * 用于修复现有数据中口味顺序不一致的问题
     */
    void normalizeExistingCartFlavors();

    List<ShoppingCart> list();

    void clean();

    /**
     * 减少购物车商品数量
     * 如果数量大于1则减1，如果数量等于1则删除该项
     * @param shoppingCartDTO 包含要减少的商品信息
     * @return 更新后的购物车列表
     */
    List<ShoppingCart> sub(ShoppingCartDTO shoppingCartDTO);
}
