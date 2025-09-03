package com.atguigu.controller.admin;

import com.atguigu.result.Result;
import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;
import com.atguigu.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/admin/workspace")
@Slf4j
@Tag(name = "工作台管理-控制层")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    /**
     * 工作台今日数据查询
     * @return Result<BusinessDataVO>
     */
    @GetMapping("/businessData")
    @Operation(summary = "今日数据查询")
    public Result<BusinessDataVO> businessData() {
        log.info("查询今日数据");
        // 获取当前时间 -> 获取今天的最开始时刻和最结束时刻
        LocalDate today = LocalDate.now();
        LocalDateTime beginTime = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(today, LocalTime.MAX);
        BusinessDataVO businessDataVO = workspaceService.businessData(beginTime, endTime);
        return Result.success(businessDataVO);
    }

    /**
     * 订单管理总览
     * @return Result<OrderOverViewVO>
     */
    @GetMapping("/overviewOrders")
    public Result<OrderOverViewVO> orderOverView() {
        log.info("订单信息总览");
        OrderOverViewVO orderOverViewVO = workspaceService.orderOverView();
        return Result.success(orderOverViewVO);
    }

    /**
     * 查询菜品总览
     * @return Result<DishOverViewVO>
     */
    @GetMapping("/overviewDishes")
    public Result<DishOverViewVO> dishOverView() {
        log.info("查询菜品总览");
        DishOverViewVO dishOverViewVO = workspaceService.dishOverView();
        return Result.success(dishOverViewVO);
    }

    /**
     * 查询套餐总览
     * @return Result<SetmealOverViewVO>
     */
    @GetMapping("/overviewSetmeals")
    public Result<SetmealOverViewVO> setmealOverView() {
        log.info("查询套餐总览");
        SetmealOverViewVO setmealOverViewVO = workspaceService.setmealOverView();
        return Result.success(setmealOverViewVO);
    }

}
