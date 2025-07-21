package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单提交返回视图对象
 * 作用：用于向前端返回订单提交成功后的关键信息，包括订单号、金额等
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
@Schema(description = "订单提交返回视图对象")
public class OrderSubmitVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 订单ID，唯一标识
    // 用于后续订单查询和跟踪
    @Schema(description = "订单ID")
    private Long id;

    // 订单号
    // 系统生成的唯一订单标识
    // 用于订单追踪和支付
    @Schema(description = "订单号")
    private String orderNumber;

    // 订单金额
    // 订单的实际支付金额
    // 使用BigDecimal确保精确计算
    // 单位：元
    @Schema(description = "订单金额")
    private BigDecimal orderAmount;

    // 下单时间
    // 用户提交订单的时间点
    // 用于订单时间追踪
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;
}
