package com.atguigu.DTO.SetMealType;

import com.atguigu.Entity.SetmealDish;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 套餐数据传输对象
 * 作用：用于接收和传递套餐相关的数据，支持套餐的创建和修改操作
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "套餐数据传输对象")
public class SetmealDTO implements Serializable {

    // 套餐ID，唯一标识
    // 创建时无需填写，修改时必填
    @Schema(description = "套餐ID")
    private Long id;

    // 分类ID
    // 关联Category表的主键，用于对套餐进行分类管理
    @Schema(description = "分类ID", required = true)
    private Long categoryId;

    // 套餐名称
    // 在前端界面展示使用，方便用户识别
    @Schema(description = "套餐名称", required = true)
    private String name;

    // 套餐价格
    // 套餐的销售价格，通常低于所含菜品的总价
    // 使用BigDecimal确保精确计算
    // 单位：元
    @Schema(description = "套餐价格", required = true)
    private BigDecimal price;

    // 套餐状态
    // 值含义：
    // - 0: 停用，暂时下架
    // - 1: 启用，正常销售
    @Schema(description = "套餐状态：0停用 1启用")
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

    // 套餐菜品关系列表
    // 存储该套餐包含的所有菜品信息
    // 默认初始化为空列表，避免NPE
    @Schema(description = "套餐菜品关系列表")
    private List<SetmealDish> setmealDishes = new ArrayList<>();
}