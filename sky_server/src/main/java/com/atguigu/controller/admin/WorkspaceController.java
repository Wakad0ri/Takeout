package com.atguigu.controller.admin;

import com.atguigu.result.Result;
import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
public class WorkspaceController {

    /**
     * 工作台今日数据查询
     * @return
     */
    @GetMapping("/businessData")
    public Result<BusinessDataVO> businessData() {
        // 临时返回模拟数据
        BusinessDataVO businessDataVO = BusinessDataVO.builder()
                .turnover(0.0)
                .validOrderCount(0)
                .orderCompletionRate(0.0)
                .unitPrice(0.0)
                .newUsers(0)
                .build();
        return Result.success(businessDataVO);
    }

    /**
     * 查询订单管理数据
     * @return
     */
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> orderOverView() {
        // 临时返回模拟数据
        OrderOverViewVO orderOverViewVO = OrderOverViewVO.builder()
                .waitingOrders(0)
                .deliveredOrders(0)
                .completedOrders(0)
                .cancelledOrders(0)
                .allOrders(0)
                .build();
        return Result.success(orderOverViewVO);
    }

    /**
     * 查询菜品总览
     * @return
     */
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> dishOverView() {
        // 临时返回模拟数据
        DishOverViewVO dishOverViewVO = DishOverViewVO.builder()
                .sold(0)
                .discontinued(0)
                .build();
        return Result.success(dishOverViewVO);
    }

    /**
     * 查询套餐总览
     * @return
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> setmealOverView() {
        // 临时返回模拟数据
        SetmealOverViewVO setmealOverViewVO = SetmealOverViewVO.builder()
                .sold(0)
                .discontinued(0)
                .build();
        return Result.success(setmealOverViewVO);
    }
} 