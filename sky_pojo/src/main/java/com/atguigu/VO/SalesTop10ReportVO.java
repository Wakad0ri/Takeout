package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 销量排行TOP10统计视图对象
 * 作用：用于展示销量最高的10个商品及其销售数据，帮助商家了解热销商品情况
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
@Schema(description = "销量排行TOP10统计视图对象")
public class SalesTop10ReportVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 商品名称列表
    // 销量前10的商品名称，按销量降序排列
    // 使用逗号分隔的字符串
    // 格式示例："鱼香肉丝,宫保鸡丁,水煮鱼"
    @Schema(description = "商品名称列表")
    private String nameList;

    // 商品销量列表
    // 与商品名称列表一一对应的销量数据
    // 使用逗号分隔的字符串
    // 格式示例："260,215,200"
    @Schema(description = "商品销量列表")
    private String numberList;
}
