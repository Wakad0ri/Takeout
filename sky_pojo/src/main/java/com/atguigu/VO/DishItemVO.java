package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 菜品项视图对象
 * 作用：用于展示菜品的简要信息，主要用在订单详情、套餐详情等场景
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
@Schema(description = "菜品项视图对象")
public class DishItemVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 菜品名称
    // 展示给用户看的菜品名称
    @Schema(description = "菜品名称")
    private String name;

    // 菜品份数
    // 记录该菜品在订单或套餐中的数量
    @Schema(description = "菜品份数")
    private Integer copies;

    // 菜品图片
    // 菜品图片的URL地址，用于界面展示
    @Schema(description = "菜品图片")
    private String image;

    // 菜品描述
    // 对菜品的简要介绍，包括主要食材、口味特点等
    @Schema(description = "菜品描述")
    private String description;
}
