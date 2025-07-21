package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户统计报表视图对象
 * 作用：用于展示用户增长的统计数据，包括总用户数和新增用户数的每日统计
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
@Schema(description = "用户统计报表视图对象")
public class UserReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 统计日期列表
    // 使用逗号分隔的日期字符串
    // 按时间顺序排列
    // 格式示例："2022-10-01,2022-10-02,2022-10-03"
    @Schema(description = "统计日期列表")
    private String dateList;

    // 用户总量列表
    // 使用逗号分隔的数字字符串，与日期列表一一对应
    // 记录每日的累计用户总数
    // 格式示例："200,210,220"
    @Schema(description = "用户总量列表")
    private String totalUserList;

    // 新增用户列表
    // 使用逗号分隔的数字字符串，与日期列表一一对应
    // 记录每日新注册的用户数量
    // 格式示例："20,21,10"
    @Schema(description = "新增用户列表")
    private String newUserList;
}
