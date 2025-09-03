package com.atguigu.mapper;

import com.atguigu.Entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查询套餐id
     * @param dishIds （List<Long>）
     * @return List<Long>
     */
    List<Long> getSetMealDishIds(List<Long> dishIds);

    /**
     * 批量插入套餐菜品关系数据
     * @param setmealDishes （List<SetmealDish>）
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * 根据套餐ID查询套餐菜品关系
     * @param setmealId 套餐ID
     * @return List<SetmealDish>
     */
    List<SetmealDish> getBySetmealId(Long setmealId);

    /**
     * 批量删除套餐菜品关系
     * @param setmealIds 套餐ID列表
     */
    void deleteBatchBySetmealIds(List<Long> setmealIds);
}
