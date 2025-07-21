package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 营业额统计报表视图对象
 * 作用：用于展示每日营业额的统计数据，支持多日数据的图表展示
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
@Schema(description = "营业额统计报表视图对象")
public class TurnoverReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 统计日期列表
    // 使用逗号分隔的日期字符串
    // 按时间顺序排列
    // 格式示例："2022-10-01,2022-10-02,2022-10-03"
    @Schema(description = "统计日期列表")
    private String dateList;

    // 营业额数据列表
    // 使用逗号分隔的数字字符串，与日期列表一一对应
    // 记录每日的营业总额
    // 单位：元
    // 格式示例："406.0,1520.0,75.0"
    @Schema(description = "营业额数据列表")
    private String turnoverList;
}
