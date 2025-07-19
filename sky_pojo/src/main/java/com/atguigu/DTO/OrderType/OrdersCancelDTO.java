package com.atguigu.DTO.OrderType;


import lombok.Data;

import java.io.Serializable;

/**
 * 订单取消对象
 * 作用：封装取消订单信息
 *
 * 重载：Serializable
 * 描述：订单取消对象
 */
@Data
public class OrdersCancelDTO implements Serializable {

    private Long id;
    //订单取消原因
    private String cancelReason;

}
