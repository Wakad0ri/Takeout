package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单取消数据传输对象
 * 作用：用于接收订单取消请求的参数，包括订单ID和取消原因
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单取消数据传输对象")
public class OrdersCancelDTO implements Serializable {

    // 订单ID，唯一标识
    // 必填字段，用于指定要取消的订单
    @Schema(description = "订单ID", required = true)
    private Long id;

    // 取消原因
    // 用户或商家取消订单时的说明
    // 用于记录取消原因，便于后续分析
    @Schema(description = "取消原因")
    private String cancelReason;
}
