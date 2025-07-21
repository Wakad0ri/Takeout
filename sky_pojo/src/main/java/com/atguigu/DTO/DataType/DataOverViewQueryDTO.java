package com.atguigu.DTO.DataType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据概览查询数据传输对象
 * 作用：用于接收数据概览的时间范围查询参数，支持系统运营数据的统计分析
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
@Schema(description = "数据概览查询数据传输对象")
public class DataOverViewQueryDTO implements Serializable {

    // 统计开始时间
    // 查询范围的起始时间点
    // 格式：yyyy-MM-dd HH:mm:ss
    @Schema(description = "统计开始时间", example = "2024-01-01 00:00:00")
    private LocalDateTime begin;

    // 统计结束时间
    // 查询范围的结束时间点
    // 格式：yyyy-MM-dd HH:mm:ss
    @Schema(description = "统计结束时间", example = "2024-01-31 23:59:59")
    private LocalDateTime end;
}
