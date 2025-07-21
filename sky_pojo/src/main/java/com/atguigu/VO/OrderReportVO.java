package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单统计报表视图对象
 * 作用：用于展示订单的统计数据，包括日期维度的订单量统计和整体订单完成率
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
@Schema(description = "订单统计报表视图对象")
public class OrderReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 统计日期列表
    // 使用逗号分隔的日期字符串
    // 格式示例：2022-10-01,2022-10-02,2022-10-03
    @Schema(description = "统计日期列表")
    private String dateList;

    // 每日订单数列表
    // 使用逗号分隔的数字字符串，与日期列表一一对应
    // 格式示例：260,210,215
    @Schema(description = "每日订单数列表")
    private String orderCountList;

    // 每日有效订单数列表
    // 使用逗号分隔的数字字符串，与日期列表一一对应
    // 有效订单指已完成的订单
    // 格式示例：20,21,10
    @Schema(description = "每日有效订单数列表")
    private String validOrderCountList;

    // 总订单数
    // 统计周期内的所有订单总量
    // 包括各种状态的订单
    @Schema(description = "总订单数")
    private Integer totalOrderCount;

    // 有效订单总数
    // 统计周期内的已完成订单总量
    // 不包括已取消、已退款的订单
    @Schema(description = "有效订单总数")
    private Integer validOrderCount;

    // 订单完成率
    // 计算公式：有效订单总数 / 总订单数 * 100%
    // 单位：百分比
    @Schema(description = "订单完成率")
    private Double orderCompletionRate;
}
