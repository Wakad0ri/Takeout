package com.atguigu.DTO.OrderType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单分页查询数据传输对象
 * 作用：用于接收订单列表的分页查询参数，支持多条件筛选和时间范围查询
 *
 * 实现接口：
 * - Serializable：支持序列化，用于网络传输
 *
 * 使用注解：
 * - @Data：自动生成getter、setter、equals、hashCode等方法
 * - @Schema：用于生成API文档
 */
@Data
@Schema(description = "订单分页查询数据传输对象")
public class OrdersPageQueryDTO implements Serializable {

    // 当前页码
    // 从1开始的页码编号
    @Schema(description = "页码", example = "1")
    private int page;

    // 每页记录数
    // 指定每页显示的数据条数
    @Schema(description = "每页记录数", example = "10")
    private int pageSize;

    // 订单号
    // 用于按订单号进行精确查询
    // 可选参数
    @Schema(description = "订单号")
    private String number;

    // 手机号码
    // 用于按用户手机号进行查询
    // 可选参数
    @Schema(description = "手机号码")
    private String phone;

    // 订单状态
    // 值含义：
    // - 1: 待付款
    // - 2: 待派送
    // - 3: 已派送
    // - 4: 已完成
    // - 5: 已取消
    // 可选参数
    @Schema(description = "订单状态")
    private Integer status;

    // 开始时间
    // 查询订单创建时间范围的起始时间
    // 格式：yyyy-MM-dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "开始时间", example = "2024-01-01 00:00:00")
    private LocalDateTime beginTime;

    // 结束时间
    // 查询订单创建时间范围的结束时间
    // 格式：yyyy-MM-dd HH:mm:ss
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "结束时间", example = "2024-01-31 23:59:59")
    private LocalDateTime endTime;

    // 用户ID
    // 关联User表的主键
    // 用于查询指定用户的订单
    // 可选参数
    @Schema(description = "用户ID")
    private Long userId;
}