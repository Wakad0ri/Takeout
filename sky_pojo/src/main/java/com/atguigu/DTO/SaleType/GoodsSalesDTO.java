package com.atguigu.DTO.SaleType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品销售数据传输对象
 * 作用：用于统计和展示商品的销售数据，包括商品名称和销售数量
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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "商品销售数据传输对象")
public class GoodsSalesDTO implements Serializable {

    // 商品名称
    // 用于标识统计的商品
    // 可能是菜品名称或套餐名称
    @Schema(description = "商品名称")
    private String name;

    // 销售数量
    // 记录商品的累计销售份数
    // 用于统计分析商品销量
    @Schema(description = "销售数量")
    private Integer number;
}