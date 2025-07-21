package com.atguigu.VO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 订单支付视图对象
 * 作用：用于封装微信支付所需的参数信息，支持小程序端发起支付请求
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
@Schema(description = "订单支付视图对象")
public class OrderPaymentVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 随机字符串
    // 微信支付API要求的随机字符串参数
    // 用于防重放攻击
    @Schema(description = "随机字符串")
    private String nonceStr;

    // 支付签名
    // 使用商户私钥对请求参数签名
    // 用于请求安全性验证
    @Schema(description = "支付签名")
    private String paySign;

    // 时间戳
    // 请求发起时的时间戳
    // 用于防重放攻击
    @Schema(description = "时间戳")
    private String timeStamp;

    // 签名算法类型
    // 用于指定签名的加密算法
    // 通常为 HMAC-SHA256 或 MD5
    @Schema(description = "签名算法")
    private String signType;

    // 预支付交易会话标识
    // 微信支付统一下单接口返回的 prepay_id 参数
    // 格式为：prepay_id=xxx
    @Schema(description = "预支付交易会话标识")
    private String packageStr;
}
