package com.atguigu.DTO.OrderType;

import com.atguigu.Entity.OrderDetail;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单数据传输对象
 * 作用：用于接收和传递订单相关的完整信息，包括订单基本信息、配送信息和订单详情
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单数据传输对象")
public class OrdersDTO implements Serializable {

    // 订单ID，唯一标识
    @Schema(description = "订单ID")
    private Long id;

    // 订单号
    // 系统生成的唯一订单标识
    @Schema(description = "订单号")
    private String number;

    // 订单状态
    // 值含义：
    // - 1: 待付款
    // - 2: 待派送
    // - 3: 已派送
    // - 4: 已完成
    // - 5: 已取消
    @Schema(description = "订单状态")
    private Integer status;

    // 下单用户ID
    // 关联User表的主键
    @Schema(description = "用户ID")
    private Long userId;

    // 收货地址ID
    // 关联AddressBook表的主键
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

    // 实收金额
    // 订单最终支付的金额
    // 单位：元
    @Schema(description = "实收金额")
    private BigDecimal amount;

    // 订单备注
    // 用户下单时的特殊要求或说明
    @Schema(description = "订单备注")
    private String remark;

    // 用户名称
    // 下单用户的用户名
    @Schema(description = "用户名称")
    private String userName;

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

    // 订单详情列表
    // 包含订单中的所有商品信息
    @Schema(description = "订单详情列表")
    private List<OrderDetail> orderDetails;
}