package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 套餐总览视图对象
 * 作用：用于展示套餐的整体运营状态统计信息，包括在售和停售的数量统计
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
@Schema(description = "套餐总览视图对象")
public class SetmealOverViewVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 在售套餐数量
    // 统计当前状态为"启售"的套餐总数
    @Schema(description = "在售套餐数量")
    private Integer sold;

    // 停售套餐数量
    // 统计当前状态为"停售"的套餐总数
    @Schema(description = "停售套餐数量")
    private Integer discontinued;
}
