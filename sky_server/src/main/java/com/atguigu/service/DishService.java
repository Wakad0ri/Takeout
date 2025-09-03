package com.atguigu.service;

import com.atguigu.DTO.DishType.DishDTO;
import com.atguigu.DTO.DishType.DishPageQueryDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.VO.DishVO;
import com.atguigu.result.PageResult;

import java.util.List;

public interface DishService {

    void saveWithFlavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    void startOrStop(Integer status, Long id);

    void deleteBatch(List<Long> ids);

    DishVO getByIdWithFlavors(Long id);

    void updateInfo(DishDTO dishDTO);

    List<Dish> list(Long categoryId, String name);

    List<DishVO> getDishVOListByDish(Dish dish);
}
