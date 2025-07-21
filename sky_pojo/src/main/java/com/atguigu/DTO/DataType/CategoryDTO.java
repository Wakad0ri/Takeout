package com.atguigu.DTO.DataType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 分类数据传输对象
 * 作用：用于新增和修改分类信息时传递数据，包括分类的基本信息
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "分类数据传输对象")
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 分类ID，修改时必填
    @Schema(description = "主键")
    private Long id;

    // 分类类型（1：菜品分类，2：套餐分类）
    @Schema(description = "类型: 1菜品分类 2套餐分类")
    private Integer type;

    // 分类名称
    @Schema(description = "分类名称")
    private String name;

    // 分类排序号，用于控制展示顺序，值越小排序越靠前
    @Schema(description = "排序")
    private Integer sort;
}