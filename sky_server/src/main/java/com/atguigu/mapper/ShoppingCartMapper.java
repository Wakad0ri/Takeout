package com.atguigu.mapper;

import com.atguigu.Entity.ShoppingCart;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    /**
     * 根据userId, dishId, setmealId, dishFlavor查询购物车
     * @param shoppingCart （userId, dishId, setmealId, dishFlavor
     * @return List<ShoppingCart>
     * 在添加购物车时使用
     */
    List<ShoppingCart> getListByUserId_DishId_SetmealId_DishFlavor(ShoppingCart shoppingCart);

    /**
     * 根据userId更新购物车数据
     * @param shoppingCart （number
     */
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     * 添加购物车
     * @param shoppingCart （
     */
    @Insert("insert into shopping_cart(name, user_id, dish_id, setmeal_id, dish_flavor, number, amount, image, create_time) values" +
            " (#{name}, #{userId}, #{dishId}, #{setmealId}, #{dishFlavor}, #{number}, #{amount}, #{image}, #{createTime})")
    void add(ShoppingCart shoppingCart);

    /**
     * 查询所有有口味的购物车项
     * @return List<ShoppingCart>
     */
    @Select("select * from shopping_cart where dish_flavor is not null and dish_flavor != ''")
    List<ShoppingCart> getAllCartItemsWithFlavors();

    /**
     * 更新购物车项的口味
     * @param shoppingCart 购物车项
     */
    @Update("update shopping_cart set dish_flavor = #{dishFlavor} where id = #{id}")
    void updateFlavorById(ShoppingCart shoppingCart);

    /**
     * 查看购物车
     * @param shoppingCart （userId
     */
    @Select("select * from shopping_cart where user_id = #{userId}")
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     * 清空购物车
     */
    @Delete("delete from shopping_cart where user_id = #{userId}")
    void clean(ShoppingCart shoppingCart);

    /**
     * 根据id删除购物车项
     * @param id 购物车项ID
     */
    @Delete("delete from shopping_cart where id = #{id}")
    void deleteById(Long id);

    /**
     * 批量插入购物车项
     * @param carts 购物车项列表
     */
    void insertBatch(List<ShoppingCart> carts);
}
