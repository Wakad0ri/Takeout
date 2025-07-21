package com.atguigu.VO;

import com.atguigu.Entity.DishFlavor;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜品视图对象
 * 作用：用于展示菜品的完整信息，包括基本信息、分类信息和口味选项
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Builder：提供流式构建对象的能力
 * - @NoArgsConstructor：生成无参构造函数
 * - @AllArgsConstructor：生成全参构造函数
 * - @Schema：用于生成API文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜品视图对象")
public class DishVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 菜品ID，唯一标识
    @Schema(description = "菜品ID")
    private Long id;

    // 菜品名称
    // 在前端界面展示使用，方便用户识别
    @Schema(description = "菜品名称")
    private String name;

    // 分类ID
    // 关联Category表的主键，用于对菜品进行分类管理
    @Schema(description = "分类ID")
    private Long categoryId;

    // 菜品价格
    // 使用BigDecimal确保精确计算
    // 单位：元
    @Schema(description = "菜品价格")
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
    @Schema(description = "菜品状态")
    private Integer status;

    // 更新时间
    // 系统自动维护，记录最后一次更新的时间点
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 分类名称
    // 冗余存储分类名称，避免多表查询
    @Schema(description = "分类名称")
    private String categoryName;

    // 菜品口味列表
    // 存储该菜品支持的所有口味选项
    // 默认初始化为空列表，避免NPE
    @Schema(description = "口味列表")
    private List<DishFlavor> flavors = new ArrayList<>();
}
