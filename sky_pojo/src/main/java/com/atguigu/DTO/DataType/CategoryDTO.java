package com.atguigu.DTO;

import lombok.Data;

/**
 * 分类数据传输对象
 * 作用：新增和修改分类信息
 */
@Data
public class CategoryDTO {

    // 主键
    private Long id;

    // 类型：1 菜品分类 2 套餐分类
    private Integer type;

    // 分类名称
    private String name;

    // 排序
    private Integer sort;
}
