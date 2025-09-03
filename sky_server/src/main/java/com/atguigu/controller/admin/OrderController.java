package com.atguigu.controller.admin;

import com.atguigu.DTO.OrderType.OrdersCancelDTO;
import com.atguigu.DTO.OrderType.OrdersConfirmDTO;
import com.atguigu.DTO.OrderType.OrdersPageQueryDTO;
import com.atguigu.DTO.OrderType.OrdersRejectionDTO;
import com.atguigu.VO.OrderDetailsVO;
import com.atguigu.VO.OrderStatisticsVO;
import com.atguigu.VO.OrderVO;
import com.atguigu.result.PageResult;
import com.atguigu.service.OrderService;
import com.atguigu.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("adminOrderController")
@RequestMapping("/admin/order")
@Slf4j
@Tag(name = "管理端-订单管理")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 订单分页查询：GET
     * @param ordersPageQueryDTO 查询参数
     * @return Result<PageResult>
     */
    @GetMapping("/conditionSearch")
    @Operation(summary = "订单分页查询")
    public Result<PageResult> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO){
        log.info("订单分页查询：{}", ordersPageQueryDTO);
        PageResult pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 各个状态的订单数量统计：GET
     * @return Result<OrderStatisticsVO>
     */
    @GetMapping("/statistics")
    @Operation(summary = "各个状态的订单数量统计")
    public Result<OrderStatisticsVO> statistics(){
        log.info("各个状态的订单数量统计");
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    /**
     * 订单详情展示： GET
     *
     * @param id （Long）
     * @return Result<OrderVO>
     */
    @GetMapping("/details/{id}")
    @Operation(summary = "订单详情展示")
    public Result<OrderVO> details(@PathVariable Long id){
        log.info("订单详情展示：{}", id);
        OrderVO orderVO = orderService.details(id);
        return Result.success(orderVO);
    }

    /**
     * 接单：PUT
     * @param ordersConfirmDTO （json）
     * @return Result<String>
     */
    @PutMapping("/confirm")
    @Operation(summary = "接单")
    public Result<String> confirm(@RequestBody OrdersConfirmDTO ordersConfirmDTO){
        log.info("接单：{}", ordersConfirmDTO);
        orderService.confirm(ordersConfirmDTO);
        return Result.success();
    }

    /**
     * 拒单：PUT
     * @param ordersRejectionDTO （json）
     * @return Result<String>
     */
    @PutMapping("/rejection")
    @Operation(summary = "拒单")
    public Result<String> rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        log.info("拒单：{}", ordersRejectionDTO);
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    /**
     * 取消订单：PUT
     * @param ordersCancelDTO （json）
     * @return Result<String>
     */
    @PutMapping("/cancel")
    @Operation(summary = "取消订单")
    public Result<String> cancel(@RequestBody OrdersCancelDTO ordersCancelDTO) throws Exception {
        log.info("取消订单：{}", ordersCancelDTO);
        orderService.cancelByAdmin(ordersCancelDTO);
        return Result.success();
    }

    /**
     * 派送订单：PUT
     * @param id （Long）
     * @return Result<String>
     */
    @PutMapping("/delivery/{id}")
    @Operation(summary = "派送订单")
    public Result<String> delivery(@PathVariable("id") Long id){
        log.info("派送订单：{}", id);
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * 完成订单：PUT
     * @param id （Long）
     * @return Result<String>
     */
    @PutMapping("/complete/{id}")
    @Operation(summary = "完成订单")
    public Result<String> complete(@PathVariable("id") Long id){
        log.info("完成订单：{}", id);
        orderService.complete(id);
        return Result.success();
    }

}
