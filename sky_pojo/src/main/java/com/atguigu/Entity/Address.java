package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 地址信息实体类
 * 作用：用于存储和管理用户的收货地址信息，包括收货人、联系方式、详细地址等
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
@Schema(description = "地址信息实体")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    private Long id;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "收货人")
    private String consignee;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "性别 0 女 1 男")
    private String sex;

    @Schema(description = "省级区划编号")
    private String provinceCode;

    @Schema(description = "省级名称")
    private String provinceName;

    @Schema(description = "市级区划编号")
    private String cityCode;

    @Schema(description = "市级名称")
    private String cityName;

    @Schema(description = "区级区划编号")
    private String districtCode;

    @Schema(description = "区级名称")
    private String districtName;

    // 详细地址
    @Schema(description = "详细地址")
    private String detail;

    // 标签
    @Schema(description = "标签")
    private String label;

    // 是否为默认地址
    // 值含义:
    // - 0: 非默认地址
    // - 1: 默认地址
    // 每个用户只能设置一个默认地址
    @Schema(description = "默认地址标识")
    private Integer isDefault;
}
