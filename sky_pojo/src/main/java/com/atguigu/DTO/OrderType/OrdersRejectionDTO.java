package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单拒绝数据传输对象
 * 作用：用于接收商家拒绝接单时的参数，包括订单ID和拒绝原因
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单拒绝数据传输对象")
public class OrdersRejectionDTO implements Serializable {

    // 订单ID，唯一标识
    // 必填字段，用于指定要拒绝的订单
    @Schema(description = "订单ID", required = true)
    private Long id;

    // 拒绝原因
    // 商家拒绝接单时的说明
    // 用于记录拒绝原因，便于后续处理和分析
    // 必填字段
    @Schema(description = "拒绝原因", required = true)
    private String rejectionReason;
}