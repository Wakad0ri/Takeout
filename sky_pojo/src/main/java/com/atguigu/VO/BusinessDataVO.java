package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 营业数据统计视图对象
 * 作用：用于展示商家的营业数据统计信息，包括营业额、订单数、完成率等关键指标
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
@Schema(description = "营业数据统计视图对象")
public class BusinessDataVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 营业额
    // 统计周期内的总营业收入
    // 单位：元
    @Schema(description = "营业额")
    private Double turnover;

    // 有效订单数
    // 统计周期内已完成的有效订单总数
    // 不包含已取消、已退款的订单
    @Schema(description = "有效订单数")
    private Integer validOrderCount;

    // 订单完成率
    // 计算公式：有效订单数 / 总订单数 * 100%
    // 单位：百分比
    @Schema(description = "订单完成率")
    private Double orderCompletionRate;

    // 平均客单价
    // 计算公式：营业额 / 有效订单数
    // 单位：元/单
    @Schema(description = "平均客单价")
    private Double unitPrice;

    // 新增用户数
    // 统计周期内新注册的用户总数
    @Schema(description = "新增用户数")
    private Integer newUsers;
}
