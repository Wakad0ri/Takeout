package com.atguigu.mapper;

import com.atguigu.Entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 批量插入口味数据（新增菜品时需要）
     * @param flavors (List<DishFlavor>）
     */
    void insertBatch(List<DishFlavor> flavors);

//    /**
//     * 根据菜品id删除口味数据
//     * @param dishId
//     */
//    @Delete("delete from dish_flavor where dish_id = #{dishId}")
//    void deleteByDishId(Long dishId);

    /**
     * 根据菜品id批量删除口味数据（删除菜品时需要）
     * @param dishIds （List<Long>）
     */
    void deleteBatch(List<Long> dishIds);

    /**
     * 根据菜品id查询口味数据（更新菜品信息时需要）
     * @param dishId （Long）
     * @return List<DishFlavor>
     */
    @Select("select * from dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getFlavorsByDishId(Long dishId);

    /**
     * 根据菜品id删除口味数据（更新菜品信息需要）
     * @param id （Long）
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);
}
