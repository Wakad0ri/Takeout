package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品信息实体类
 * 作用：用于存储和管理菜品的基本信息，包括名称、价格、状态等核心数据
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
@Schema(description = "菜品信息实体")
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    // 菜品ID，唯一标识
    @Schema(description = "菜品ID")
    private Long id;

    // 菜品名称
    // 在前端界面展示使用，方便用户识别
    @Schema(description = "菜品名称")
    private String name;

    // 菜品分类ID
    // 关联Category表的主键，用于对菜品进行分类管理
    @Schema(description = "菜品分类ID")
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

    // 创建时间
    // 系统自动生成，记录菜品创建的时间点
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 更新时间
    // 系统自动维护，记录最后一次更新的时间点
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人ID
    // 记录创建该菜品的用户ID，用于追踪操作人
    @Schema(description = "创建人ID")
    private Long createUser;

    // 修改人ID
    // 记录最后一次修改该菜品的用户ID，用于追踪操作人
    @Schema(description = "修改人ID")
    private Long updateUser;
}
