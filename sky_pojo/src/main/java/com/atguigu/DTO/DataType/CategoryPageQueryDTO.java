package com.atguigu.DTO;


import lombok.Data;

/**
 * 分类分页查询对象
 * 作用：分类信息的分页查询
 */
@Data
public class CategoryPageQueryDTO {

    // 页码
    private Integer page;

    // 每页数量
    private Integer pageSize;

    // 分类名称
    private String name;

    // 分类类型 1 菜品分类 2 套餐分类
    private Integer type;

}
