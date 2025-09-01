package com.atguigu.mapper;

import com.atguigu.DTO.CategoryType.CategoryPageQueryDTO;
import com.atguigu.Entity.Category;
import com.atguigu.annotation.AutoFill;
import com.atguigu.enumeration.OperationType;
import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
@Tag(name = "分类管理-数据访问层")
public interface CategoryMapper {

    /**
     * 新增分类
     * @param category （
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * 分类分页查询
     * @param categoryPageQueryDTO （
     * @return Page<Category>
     */
    Page<Category> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 启用或禁用分类
     * @param category （为什么不是CategoryDTO呢？ 因为CategoryDTO没有status属性
     *                  为什么不是Long id, Integer status呢？ 因为还要更新updateTime和updateUser）
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateStatus(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id = #{id}")
    void deleteById(Long id);

    /**
     * 修改分类
     * @param category （
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateInfo(Category category);

    /**
     * 根据类型查询分类列表
     * @param type 分类类型（1 菜品分类 2 套餐分类，null则查询所有）
     * @return List<Category>
     */
    List<Category> list(Integer type);
}
