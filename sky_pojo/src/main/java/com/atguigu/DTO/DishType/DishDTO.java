package com.atguigu.DTO.DishType;

import com.atguigu.Entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品数据传输对象
 * 作用：用于接收和传递菜品相关的数据，支持菜品的创建和修改操作
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "菜品数据传输对象")
public class DishDTO implements Serializable {

    // 菜品ID，唯一标识
    // 创建时无需填写，修改时必填
    @Schema(description = "菜品ID")
    private Long id;

    // 菜品名称
    // 必填字段，在前端界面展示使用
    @Schema(description = "菜品名称", required = true)
    private String name;

    // 分类ID
    // 关联Category表的主键，用于对菜品进行分类管理
    @Schema(description = "分类ID", required = true)
    private Long categoryId;

    // 菜品价格
    // 必填字段
    // 使用BigDecimal确保精确计算
    // 单位：元
    @Schema(description = "菜品价格", required = true)
    private BigDecimal price;

    // 菜品图片
    // 存储图片的相对路径或URL
    // 用于在前端展示菜品图片
    @Schema(description = "图片路径")
    private String image;

    // 菜品描述
    // 详细介绍菜品的特点、原料等信息
    @Schema(description = "描述信息")
    private String description;

    // 菜品状态
    // 值含义：
    // - 0: 停售，暂时下架
    // - 1: 起售，正常销售
    @Schema(description = "菜品状态：0停售 1起售")
    private Integer status;

    // 口味列表
    // 存储该菜品支持的所有口味选项
    // 默认初始化为空列表，避免NPE
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors = new ArrayList<>();
}
