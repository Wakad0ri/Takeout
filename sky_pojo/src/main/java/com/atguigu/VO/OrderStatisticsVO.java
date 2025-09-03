package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 订单状态统计视图对象
 * 作用：用于展示不同状态订单的数量统计，帮助商家了解当前需要处理的订单情况
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单状态统计视图对象")
public class OrderStatisticsVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 待接单订单数量
    // 新订单，等待商家确认接单
    // 对应订单状态：2（待接单）
    @Schema(description = "待接单数量")
    private Integer toBeConfirmed;

    // 待派送订单数量
    // 商家已接单，等待配送员取餐
    // 对应订单状态：3（已接单）
    @Schema(description = "待派送数量")
    private Integer confirmed;

    // 派送中订单数量
    // 配送员已取餐，正在配送途中
    // 对应订单状态：4（派送中）
    @Schema(description = "派送中数量")
    private Integer deliveryInProgress;
}
