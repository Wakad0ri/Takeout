package com.atguigu.controller.user;

import com.atguigu.DTO.OrderType.OrderHistoryQueryDTO;
import com.atguigu.DTO.OrderType.OrdersPaymentDTO;
import com.atguigu.DTO.OrderType.OrdersSubmitDTO;
import com.atguigu.VO.OrderDetailsVO;
import com.atguigu.VO.OrderPaymentVO;
import com.atguigu.VO.OrderSubmitVO;
import com.atguigu.VO.OrderVO;
import com.atguigu.result.PageResult;
import com.atguigu.result.Result;
import com.atguigu.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userOrderController")
@RequestMapping("/user/order")
@Tag(name = "用户端-订单管理")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 用户下单：POST
     * @param orderSubmitDTO (json)
     * @return Result<OrderSubmitVO>
     */
    @PostMapping("/submit")
    @Operation(summary = "用户下单")
    public Result<OrderSubmitVO> submit(@RequestBody OrdersSubmitDTO orderSubmitDTO){
        log.info("用户下单：{}", orderSubmitDTO);
        OrderSubmitVO orderSubmitVO = orderService.submit(orderSubmitDTO);
        return Result.success(orderSubmitVO);
    }

    /**
     * 订单支付：PUT
     * @param ordersPaymentDTO （Json）
     * @return Result<OrderPaymentVO>
     */
    @PutMapping("/payment")
    @Operation(summary = "订单支付")
    public Result<OrderPaymentVO> payment(@RequestBody OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        log.info("订单支付：{}", ordersPaymentDTO);
//      这个地方因为要支付必须要进行校验，所以直接调用paySuccess方法进而跳转成功页面
//        OrderPaymentVO orderPaymentVO = orderService.payment(ordersPaymentDTO);
//        log.info("生成预支付交易单：{}", orderPaymentVO);
//        return Result.success(orderPaymentVO);
        orderService.paySuccess(ordersPaymentDTO.getOrderNumber());
        return Result.success(new OrderPaymentVO());
    }

    /**
     * 历史订单查询：GET
     * @param orderHistoryQueryDTO （Json）
     * @return Result<PageResult>
     */
    @GetMapping("/historyOrders")
    @Operation(summary = "历史订单查询")
    public Result<PageResult> historyOrders(OrderHistoryQueryDTO orderHistoryQueryDTO){
        log.info("历史订单查询：{}", orderHistoryQueryDTO);
        PageResult pageResult = orderService.historyOrders(orderHistoryQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 订单详情：GET
     * @param id （订单id）
     * @return Result<OrderVO>
     */
    @GetMapping("/orderDetail/{id}")
    @Operation(summary = "订单详情")
    public Result<OrderVO> details(@PathVariable Long id){
        log.info("查询订单详情，订单ID：{}", id);
        OrderVO orderDetailsVO = orderService.details(id);
        return Result.success(orderDetailsVO);
    }

    /**
     * 取消订单：PUT
     * @param id （
     * @return Result<String>
     */
    @PutMapping("/cancel/{id}")
    @Operation(summary = "取消订单")
    public Result<String> cancel(@PathVariable Long id){
        log.info("取消订单：{}", id);
        orderService.cancelByUser(id);
        return Result.success();
    }

    /**
     * 再来一单：POST
     * @param id （也就是订单id）
     * @return Result<String>
     */
    @PostMapping("/repetition/{id}")
    @Operation(summary = "再来一单")
    public Result<String> again(@PathVariable Long id){
        orderService.again(id);
        return Result.success();
    }

    /**
     * 用户催单：GET
     * @param id （订单id）
     */
    @GetMapping("/reminder/{id}")
    @Operation(summary = "=用户催单")
    public Result<String> reminder(@PathVariable Long id){
        log.info("用户催单：{}", id);
        orderService.reminder(id);
        return Result.success();
    }



}
