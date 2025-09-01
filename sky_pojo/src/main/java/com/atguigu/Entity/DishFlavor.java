package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * 菜品口味信息实体类
 * 作用：用于存储和管理菜品的口味选项信息，如辣度、甜度等可定制的口味属性
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
@Schema(description = "菜品口味信息实体")
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    // 口味ID，唯一标识
    @Schema(description = "口味ID")
    private Long id;

    // 关联的菜品ID
    // 关联Dish表的主键，指定该口味属于哪个菜品
    @Schema(description = "菜品ID")
    private Long dishId;

    // 口味名称
    // 如：辣度、甜度、温度等口味选项的名称
    @Schema(description = "口味名称")
    private String name;

    // 口味选项值列表
    // 使用JSON字符串存储，如：["不辣", "微辣", "中辣", "重辣"]
    // 前端可根据这些选项值展示具体的口味选择
    @Schema(description = "口味选项值")
    private String value;
}
