package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单信息实体类
 * 作用：用于存储和管理订单的完整信息，包括订单状态、支付信息、配送信息等
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
@Schema(description = "订单信息实体")
public class Orders implements Serializable {

    // 订单状态常量定义
    // 用于标识订单在系统中的处理阶段
    public static final Integer PENDING_PAYMENT = 1;        // 待付款
    public static final Integer TO_BE_CONFIRMED = 2;        // 待接单
    public static final Integer CONFIRMED = 3;              // 已接单
    public static final Integer DELIVERY_IN_PROGRESS = 4;   // 派送中
    public static final Integer COMPLETED = 5;              // 已完成
    public static final Integer CANCELLED = 6;              // 已取消

    // 支付状态常量定义
    // 用于标识订单的支付情况
    public static final Integer UN_PAID = 0;    // 未支付
    public static final Integer PAID = 1;       // 已支付
    public static final Integer REFUND = 2;     // 已退款

    private static final long serialVersionUID = 1L;

    // 订单ID，唯一标识
    @Schema(description = "订单ID")
    private Long id;

    // 订单号
    // 系统生成的唯一订单标识，用于追踪和查询
    @Schema(description = "订单号")
    private String number;

    // 订单状态
    // 值含义：
    // - 1: 待付款
    // - 2: 待接单
    // - 3: 已接单
    // - 4: 派送中
    // - 5: 已完成
    // - 6: 已取消
    // - 7: 退款
    @Schema(description = "订单状态")
    private Integer status;

    // 下单用户ID
    // 关联User表的主键，标识订单所属用户
    @Schema(description = "用户ID")
    private Long userId;

    // 收货地址ID
    // 关联AddressBook表的主键，标识订单配送地址
    @Schema(description = "地址ID")
    private Long addressBookId;

    // 下单时间
    // 用户提交订单的时间点
    @Schema(description = "下单时间")
    private LocalDateTime orderTime;

    // 结账时间
    // 用户完成支付的时间点
    @Schema(description = "结账时间")
    private LocalDateTime checkoutTime;

    // 支付方式
    // 值含义：
    // - 1: 微信支付
    // - 2: 支付宝支付
    @Schema(description = "支付方式")
    private Integer payMethod;

    // 支付状态
    // 值含义：
    // - 0: 未支付
    // - 1: 已支付
    // - 2: 已退款
    @Schema(description = "支付状态")
    private Integer payStatus;

    // 实收金额
    // 订单最终支付的金额，包含商品金额和各种费用
    @Schema(description = "实收金额")
    private BigDecimal amount;

    // 订单备注
    // 用户下单时的特殊要求或说明
    @Schema(description = "订单备注")
    private String remark;

    // 手机号码
    // 用于配送联系
    @Schema(description = "手机号码")
    private String phone;

    // 配送地址
    // 完整的配送地址信息
    @Schema(description = "配送地址")
    private String address;

    // 收货人姓名
    // 配送物品接收人
    @Schema(description = "收货人")
    private String consignee;

    // 订单取消原因
    // 用户取消订单时的说明
    @Schema(description = "取消原因")
    private String cancelReason;

    // 订单拒绝原因
    // 商家拒绝接单时的说明
    @Schema(description = "拒绝原因")
    private String rejectionReason;

    // 订单取消时间
    // 记录订单被取消的具体时间点
    @Schema(description = "取消时间")
    private LocalDateTime cancelTime;

    // 预计送达时间
    // 商家预估的配送到达时间
    @Schema(description = "预计送达时间")
    private LocalDateTime estimatedDeliveryTime;

    // 配送状态
    // 值含义：
    // - 0: 选择具体配送时间
    // - 1: 立即配送
    @Schema(description = "配送状态")
    private Integer deliveryStatus;

    // 实际送达时间
    // 记录订单实际配送到达的时间点
    @Schema(description = "送达时间")
    private LocalDateTime deliveryTime;

    // 打包费用
    // 餐品打包收取的费用
    @Schema(description = "打包费")
    private int packAmount;

    // 餐具数量
    // 随订单配送的餐具数量
    @Schema(description = "餐具数量")
    private int tablewareNumber;

    // 餐具数量状态
    // 值含义：
    // - 0: 选择具体数量
    // - 1: 按餐量提供
    @Schema(description = "餐具数量状态")
    private Integer tablewareStatus;
}
