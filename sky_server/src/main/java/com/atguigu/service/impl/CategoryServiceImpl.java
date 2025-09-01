package com.atguigu.service.impl;

import com.atguigu.DTO.CategoryType.CategoryDTO;
import com.atguigu.DTO.CategoryType.CategoryPageQueryDTO;
import com.atguigu.Entity.Category;
import com.atguigu.context.BaseContext;
import com.atguigu.constant.StatusConstant;
import com.atguigu.mapper.CategoryMapper;
import com.atguigu.mapper.EmployeeMapper;
import com.atguigu.result.PageResult;
import com.atguigu.service.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Tag(name = "分类管理-服务层")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     * @param categoryDTO （json）
     */
    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());
        category.setStatus(StatusConstant.DISABLE);
        categoryMapper.insert(category);
    }

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO （
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 启用或禁用分类
     * @param status （Integer）
     * @param id （Long）
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .status(status)
                .id(id)
//                .updateTime(LocalDateTime.now())
//                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.updateStatus(category);
    }

    /**
     * 分类删除
     * @param id （Long）
     */
    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);
    }

    /**
     * 分类修改
     * @param categoryDTO （CategoryDTO）
     */
    @Override
    public void updateInfo(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        categoryMapper.updateInfo(category);
    }

    /**
     * 根据类型查询分类列表
     * @param type 分类类型（1 菜品分类 2 套餐分类）
     * @return List<Category>
     *
     */
    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.list(type);
    }
}
