package com.atguigu.service;

import com.atguigu.DTO.OrderType.*;
import com.atguigu.VO.*;
import com.atguigu.result.PageResult;

public interface OrderService {

    // 以下是用户端
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult historyOrders(OrderHistoryQueryDTO orderHistoryQueryDTO);

    OrderVO details(Long id);   // 订单详情展示：该方法用户端管理端都需要

    void cancelByUser(Long id);

    void again(Long orderId);

    void reminder(Long id);

    // 以下是管理端
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void cancelByAdmin(OrdersCancelDTO ordersCancelDTO) throws Exception;

    void delivery(Long id);

    void complete(Long id);

}
package com.atguigu.service;

import com.atguigu.DTO.OrderType.*;
import com.atguigu.VO.*;
import com.atguigu.result.PageResult;

public interface OrderService {

    // 以下是用户端
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult historyOrders(OrderHistoryQueryDTO orderHistoryQueryDTO);

    OrderVO details(Long id);   // 订单详情展示：该方法用户端管理端都需要

    void cancelByUser(Long id);

    void again(Long orderId);

    void reminder(Long id);

    // 以下是管理端
    PageResult conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void cancelByAdmin(OrdersCancelDTO ordersCancelDTO) throws Exception;

    void delivery(Long id);

    void complete(Long id);

}
