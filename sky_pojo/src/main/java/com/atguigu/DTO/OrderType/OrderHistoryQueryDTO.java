package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 历史订单查询数据传输对象
 * 作用：用于封装用户查询历史订单时的分页和状态筛选参数
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "历史订单查询数据传输对象")
public class OrderHistoryQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 页码
    // 当前查询的页数，从1开始
    @Schema(description = "页码", example = "1", required = true)
    private int page;

    // 每页记录数
    // 每页显示的订单数量
    @Schema(description = "每页记录数", example = "10", required = true)
    private int pageSize;

    // 订单状态
    // 用于筛选特定状态的订单，可选参数
    // 值含义：
    // - 1: 待付款
    // - 2: 待接单
    // - 3: 已接单
    // - 4: 派送中
    // - 5: 已完成
    // - 6: 已取消
    @Schema(description = "订单状态：1待付款 2待接单 3已接单 4派送中 5已完成 6已取消")
    private Integer status;
}