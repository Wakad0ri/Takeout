package com.atguigu.Entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 分类信息实体类
 * 作用：用于存储和管理菜品及套餐的分类信息，支持分类的增删改查和状态管理
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
@Schema(description = "分类信息实体")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    // 分类ID，唯一标识
    @Schema(description = "分类ID")
    private Long id;

    // 分类类型
    // 值含义：
    // - 1: 菜品分类
    // - 2: 套餐分类
    @Schema(description = "分类类型")
    private Integer type;

    // 分类名称
    // 用于展示在前端界面，方便用户识别
    @Schema(description = "分类名称")
    private String name;

    // 显示顺序
    // 数值越小越靠前
    // 用于自定义分类在前端界面的展示顺序
    @Schema(description = "显示顺序")
    private Integer sort;

    // 分类状态
    // 值含义：
    // - 0: 禁用，分类不可用
    // - 1: 启用，分类正常使用
    @Schema(description = "分类状态")
    private Integer status;

    // 创建时间
    // 系统自动生成，记录分类创建的时间点
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    // 更新时间
    // 系统自动维护，记录最后一次更新的时间点
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    // 创建人ID
    // 记录创建该分类的用户ID，用于追踪操作人
    @Schema(description = "创建人ID")
    private Long createUser;

    // 修改人ID
    // 记录最后一次修改该分类的用户ID，用于追踪操作人
    @Schema(description = "修改人ID")
    private Long updateUser;
}
