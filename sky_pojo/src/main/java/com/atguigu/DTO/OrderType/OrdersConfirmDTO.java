package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单确认数据传输对象
 * 作用：用于接收订单确认操作的参数，包括订单ID和目标状态
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单确认数据传输对象")
public class OrdersConfirmDTO implements Serializable {

    // 订单ID，唯一标识
    // 必填字段，用于指定要确认的订单
    @Schema(description = "订单ID", required = true)
    private Long id;

    // 订单状态
    // 值含义：
    // - 1: 待付款
    // - 2: 待接单
    // - 3: 已接单
    // - 4: 派送中
    // - 5: 已完成
    // - 6: 已取消
    // - 7: 退款
    @Schema(description = "订单状态", required = true)
    private Integer status;
}
