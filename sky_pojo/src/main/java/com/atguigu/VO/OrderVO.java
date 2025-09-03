package com.atguigu.VO;

import com.atguigu.Entity.OrderDetail;
import com.atguigu.Entity.Orders;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 订单视图对象
 * 作用：用于展示订单的完整信息，继承自Orders实体并扩展了订单详情信息
 *
 * 继承类：
 * - Orders：继承订单的基本信息
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @NoArgsConstructor：生成无参构造函数
 * - @AllArgsConstructor：生成全参构造函数
 * - @Schema：用于生成API文档
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单视图对象")
public class OrderVO extends Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单菜品信息摘要
    // 用于在订单列表中展示的简要信息
    // 格式示例："宫保鸡丁*1，水煮鱼*1"
    @Schema(description = "订单菜品信息摘要")
    private String orderDishes;

    // 订单详情列表
    // 包含订单中的所有菜品详细信息
    // 用于订单详情页面的展示
    @Schema(description = "订单详情列表")
    private List<OrderDetail> orderDetailList;
}
