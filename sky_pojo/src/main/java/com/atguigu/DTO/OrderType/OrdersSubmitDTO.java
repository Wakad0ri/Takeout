package com.atguigu.DTO.OrderType;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单提交数据传输对象
 * 作用：用于接收用户提交订单时的参数，包括配送信息、支付方式、餐具信息等
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单提交数据传输对象")
public class OrdersSubmitDTO implements Serializable {

    // 收货地址ID
    // 关联AddressBook表的主键
    // 必填字段，用于指定订单配送地址
    @Schema(description = "收货地址ID", required = true)
    private Long addressBookId;

    // 支付方式
    // 值含义：
    // - 1: 微信支付
    // - 2: 支付宝支付
    // 必填字段
    @Schema(description = "支付方式：1微信支付 2支付宝支付", required = true)
    private int payMethod;

    // 订单备注
    // 用户下单时的特殊要求或说明
    @Schema(description = "订单备注")
    private String remark;

    // 预计送达时间
    // 当配送状态为0时必填
    // 格式：yyyy-MM-dd HH:mm:ss
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "预计送达时间", example = "2024-01-01 12:00:00")
    private LocalDateTime estimatedDeliveryTime;

    // 配送状态
    // 值含义：
    // - 0: 选择具体配送时间
    // - 1: 立即配送
    @Schema(description = "配送状态：0选择具体时间 1立即送出")
    private Integer deliveryStatus;

    // 餐具数量
    // 当餐具状态为0时必填
    @Schema(description = "餐具数量")
    private Integer tablewareNumber;

    // 餐具数量状态
    // 值含义：
    // - 0: 选择具体数量
    // - 1: 按餐量提供
    @Schema(description = "餐具数量状态：0选择具体数量 1按餐量提供")
    private Integer tablewareStatus;

    // 打包费用
    // 餐品打包收取的费用
    // 单位：元
    @Schema(description = "打包费")
    private Integer packAmount;

    // 订单总金额
    // 包含商品金额和打包费等
    // 单位：元
    @Schema(description = "订单总金额")
    private BigDecimal amount;
}