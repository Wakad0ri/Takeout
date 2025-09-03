package com.atguigu.VO;

import com.atguigu.Entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单详情视图对象
 * 作用：用于给前端展示数据
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单详情视图对象")
public class OrderDetailsVO implements Serializable {

    // 订单ID
    @Schema(description = "订单ID")
    private Long id;

    // 订单号
    @Schema(description = "订单号")
    private String number;

    // 订单状态
    @Schema(description = "订单状态")
    private Integer status;

    // 用户ID
    @Schema(description = "用户ID")
    private Long userId;

    // 地址ID
    @Schema(description = "地址ID")
    private Long addressBookId;

    // 下单时间
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    // 结账时间
    @Schema(description = "结账时间")
    private LocalDateTime checkoutTime;

    // 支付方式
    @Schema(description = "支付方式")
    private Integer payMethod;

    // 支付状态
    @Schema(description = "支付状态")
    private Integer payStatus;

    // 实收金额
    @Schema(description = "实收金额")
    private BigDecimal amount;

    // 订单备注
    @Schema(description = "订单备注")
    private String remark;

    // 手机号码
    @Schema(description = "手机号码")
    private String phone;

    // 配送地址
    @Schema(description = "配送地址")
    private String address;

    // 收货人
    @Schema(description = "收货人")
    private String consignee;

    // 取消原因
    @Schema(description = "取消原因")
    private String cancelReason;

    // 拒绝原因
    @Schema(description = "拒绝原因")
    private String rejectionReason;

    // 取消时间
    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    // 预计送达时间
    @Schema(description = "预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    // 配送状态
    @Schema(description = "配送状态")
    private Integer deliveryStatus;

    // 送达时间
    @Schema(description = "送达时间")
    private LocalDateTime deliveryTime;

    // 打包费
    @Schema(description = "打包费")
    private int packAmount;

    // 餐具数量
    @Schema(description = "餐具数量")
    private int tablewareNumber;

    // 餐具数量状态
    @Schema(description = "餐具数量状态")
    private Integer tablewareStatus;

    // 订单菜品信息摘要
    @Schema(description = "订单菜品信息摘要")
    private String orderDishes;

    // 订单菜品信息列表
    @Schema(description = "订单菜品信息列表")
    private List<OrderDetail> orderDishList;

}
