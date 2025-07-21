package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单支付数据传输对象
 * 作用：用于接收订单支付请求的参数，包括订单号和支付方式
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单支付数据传输对象")
public class OrdersPaymentDTO implements Serializable {

    // 订单号
    // 系统生成的唯一订单标识
    // 必填字段，用于指定要支付的订单
    @Schema(description = "订单号", required = true)
    private String orderNumber;

    // 支付方式
    // 值含义：
    // - 1: 微信支付
    // - 2: 支付宝支付
    // 必填字段
    @Schema(description = "支付方式：1微信支付 2支付宝支付", required = true)
    private Integer payMethod;
}