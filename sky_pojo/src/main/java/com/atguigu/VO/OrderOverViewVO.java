package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单概览视图对象
 * 作用：用于展示订单的整体状态统计信息，包括各个状态的订单数量
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
@Schema(description = "订单概览视图对象")
public class OrderOverViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 待接单订单数量
    // 新订单，等待商家接单的数量
    @Schema(description = "待接单数量")
    private Integer waitingOrders;

    // 待派送订单数量
    // 商家已接单，等待配送的订单数量
    @Schema(description = "待派送数量")
    private Integer deliveredOrders;

    // 已完成订单数量
    // 订单已送达，交易完成的数量
    @Schema(description = "已完成数量")
    private Integer completedOrders;

    // 已取消订单数量
    // 包括用户取消和商家取消的订单总数
    @Schema(description = "已取消数量")
    private Integer cancelledOrders;

    // 总订单数量
    // 所有状态订单的总和
    @Schema(description = "总订单数量")
    private Integer allOrders;
}
