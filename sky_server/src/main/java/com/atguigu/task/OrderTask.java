package com.atguigu.task;

import com.atguigu.Entity.Orders;
import com.atguigu.mapper.OrderMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类：定时处理订单状态
 */
@Component
@Slf4j
@Tag(name = "定时任务类")
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 处理超时订单的方法
     * 功能：每分钟处理一次
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
        log.info("处理超时订单：{}", LocalDateTime.now());
        // 查询超时订单
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(
                Orders.PENDING_PAYMENT,
                LocalDateTime.now().plusMinutes(-15)    // .plusMinutes(-15)表示15分钟前的时间
        );

        // 若存在超时订单，则进行取消
        if (list != null && !list.isEmpty()) {
            for (Orders orders : list) {
                orders.setStatus(Orders.CANCELLED);
                orders.setCancelReason("支付超时，取消订单");
                orders.setCancelTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

    /**
     * 处理一直处于派送中的订单
     * 功能：每天凌晨1点处理一次
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder() {
        log.info("处理处于派送中的订单：{}", LocalDateTime.now());
        List<Orders> list = orderMapper.getByStatusAndOrderTimeLT(
                Orders.DELIVERY_IN_PROGRESS,
                LocalDateTime.now().plusMinutes(-60)    // 表示在@Scheduled注解中设置的时间之前60分钟
        );
        if (list != null && !list.isEmpty()) {
            for (Orders orders : list) {
                orders.setStatus(Orders.COMPLETED);
                orders.setDeliveryTime(LocalDateTime.now());
                orderMapper.update(orders);
            }
        }
    }

}
