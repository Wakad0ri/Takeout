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
 * 套餐信息实体类
 * 作用：用于存储和管理商品套餐的基本信息，包括价格、状态、分类等核心数据
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
@Schema(description = "套餐信息实体")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    // 套餐ID，唯一标识
    @Schema(description = "套餐ID")
    private Long id;

    // 分类ID
    // 关联Category表的主键，用于对套餐进行分类管理
    @Schema(description = "分类ID")
    private Long categoryId;

    // 套餐名称
    // 在前端界面展示使用，方便用户识别
    @Schema(description = "套餐名称")
    private String name;

    // 套餐价格
    // 套餐的销售价格，通常低于所含菜品的总价
    // 使用BigDecimal确保精确计算
    @Schema(description = "套餐价格")
    private BigDecimal price;

    // 套餐状态
    // 值含义：
    // - 0: 停用，暂时下架
    // - 1: 启用，正常销售
    @Schema(description = "套餐状态")
    private Integer status;

    // 套餐描述
    // 详细介绍套餐的组成、特点等信息
    @Schema(description = "套餐描述")
    private String description;

    // 套餐图片
    // 存储图片的相对路径或URL
    // 用于在前端展示套餐图片
    @Schema(description = "图片路径")
    private String image;

    // 创建时间
    // 系统自动生成，记录套餐创建的时间点
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 更新时间
    // 系统自动维护，记录最后一次更新的时间点
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人ID
    // 记录创建该套餐的用户ID，用于追踪操作人
    @Schema(description = "创建人ID")
    private Long createUser;

    // 修改人ID
    // 记录最后一次修改该套餐的用户ID，用于追踪操作人
    @Schema(description = "修改人ID")
    private Long updateUser;
}
