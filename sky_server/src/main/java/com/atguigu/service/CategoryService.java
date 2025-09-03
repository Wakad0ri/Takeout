package com.atguigu.service;

import com.atguigu.DTO.CategoryType.CategoryDTO;
import com.atguigu.DTO.CategoryType.CategoryPageQueryDTO;
import com.atguigu.Entity.Category;
import com.atguigu.Entity.Setmeal;
import com.atguigu.result.PageResult;

import java.util.List;

public interface CategoryService {

    void save(CategoryDTO category);

    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    void startOrStop(Integer status, Long id);

    void deleteById(Long id);

    void updateInfo(CategoryDTO categoryDTO);

    List<Category> list(Integer type);
}
