package com.atguigu.service;

import com.atguigu.DTO.SetMealType.SetmealDTO;
import com.atguigu.DTO.SetMealType.SetmealPageQueryDTO;
import com.atguigu.Entity.Setmeal;
import com.atguigu.VO.DishItemVO;
import com.atguigu.VO.SetmealVO;
import com.atguigu.result.PageResult;

import java.util.List;

public interface SetmealService {

    void save(SetmealDTO setmealDTO);

    PageResult page(SetmealPageQueryDTO setmealPageQueryDTO);

    void startOrStop(Integer status, Long id);

    void deleteBatch(List<Long> ids);

    SetmealVO getByIdWithDishes(Long id);

    void updateInfo(SetmealDTO setmealDTO);

    List<Setmeal> list(Setmeal setmeal);

    List<DishItemVO> getDishItemVOListBySetmealId(Long id);
}
