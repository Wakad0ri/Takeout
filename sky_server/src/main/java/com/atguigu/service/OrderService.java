package com.atguigu.service;

import com.atguigu.DTO.OrderType.*;
import com.atguigu.VO.OrderPaymentVO;
import com.atguigu.VO.OrderStatisticsVO;
import com.atguigu.VO.OrderSubmitVO;
import com.atguigu.VO.OrderVO;
import com.atguigu.result.PageResult;

public interface OrderService {

    // 以下是用户端
    OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO);

    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    void paySuccess(String outTradeNo);

    PageResult historyOrders(OrderHistoryQueryDTO orderHistoryQueryDTO);

    void cancel(Long id);

    void again(Long orderId);

    // 以下是管理端
    PageResult page(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    OrderVO details(Long id);

    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception;

    void delivery(Long id);

    void complete(Long id);
}
