package com.atguigu.service.impl;

import com.atguigu.Entity.Orders;
import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;
import com.atguigu.constant.StatusConstant;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.mapper.SetmealMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.WorkspaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Tag(name = "工作台管理-服务层")
public class WorkspaceServiceImpl implements WorkspaceService {

//    @Autowired
//    private WorkspaceMapper workspaceMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 今日数据查询
     * @param beginTime LocalDateTime
     * @param endTime LocalDateTime
     * @return BusinessDataVO
     */
    @Override
    public BusinessDataVO businessData(LocalDateTime beginTime, LocalDateTime endTime) {

        // 查询总订单数
        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        Integer totalOrderCount = orderMapper.getOrderCountByMap(map);

        // 查询当前 营业额 和 有效订单数
        map.put("status", Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(map);
        Integer validOrderCount = orderMapper.getOrderCountByMap(map);

        // 查询新增用户数
        Integer newUsers = userMapper.getUserCountByMap(map);

        // 封装成 BusinessDataVO，要求：totalOrderCount和validOrderCount不能为0
        double orderCompletionRate = 0.0;
        double unitPrice = 0.0;
        if (totalOrderCount != 0 && validOrderCount != 0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover / validOrderCount;
        }
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO orderOverView() {
        // 统计 “待接单”, “待派送”, “已完成”, “已取消” 和 总订单 数量
        Integer waitingOrders = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer deliveredOrders = orderMapper.countStatus(Orders.CONFIRMED);
        Integer completedOrders = orderMapper.countStatus(Orders.COMPLETED);
        Integer cancelledOrders = orderMapper.countStatus(Orders.CANCELLED);
        Integer allOrders = orderMapper.countStatus(null);
        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    @Override
    public DishOverViewVO dishOverView() {
        // 统计 “在售”, “停售” 数量
        Integer sold = dishMapper.countStatus(StatusConstant.ENABLE);
        Integer discontinued = dishMapper.countStatus(StatusConstant.DISABLE);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public SetmealOverViewVO setmealOverView() {
        // 统计 “在售”, “停售” 数量
        Integer sold = setmealMapper.countStatus(StatusConstant.ENABLE);
        Integer discontinued = setmealMapper.countStatus(StatusConstant.DISABLE);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }


}
package com.atguigu.service.impl;

import com.atguigu.Entity.Orders;
import com.atguigu.VO.BusinessDataVO;
import com.atguigu.VO.DishOverViewVO;
import com.atguigu.VO.OrderOverViewVO;
import com.atguigu.VO.SetmealOverViewVO;
import com.atguigu.constant.StatusConstant;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.OrderMapper;
import com.atguigu.mapper.SetmealMapper;
import com.atguigu.mapper.UserMapper;
import com.atguigu.service.WorkspaceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Tag(name = "工作台管理-服务层")
public class WorkspaceServiceImpl implements WorkspaceService {

//    @Autowired
//    private WorkspaceMapper workspaceMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 今日数据查询
     * @param beginTime LocalDateTime
     * @param endTime LocalDateTime
     * @return BusinessDataVO
     */
    @Override
    public BusinessDataVO businessData(LocalDateTime beginTime, LocalDateTime endTime) {

        // 查询总订单数
        Map<String, Object> map = new HashMap<>();
        map.put("begin", beginTime);
        map.put("end", endTime);
        Integer totalOrderCount = orderMapper.getOrderCountByMap(map);

        // 查询当前 营业额 和 有效订单数
        map.put("status", Orders.COMPLETED);
        Double turnover = orderMapper.sumByMap(map);
        Integer validOrderCount = orderMapper.getOrderCountByMap(map);

        // 查询新增用户数
        Integer newUsers = userMapper.getUserCountByMap(map);

        // 封装成 BusinessDataVO，要求：totalOrderCount和validOrderCount不能为0
        double orderCompletionRate = 0.0;
        double unitPrice = 0.0;
        if (totalOrderCount != 0 && validOrderCount != 0){
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            unitPrice = turnover / validOrderCount;
        }
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }

    @Override
    public OrderOverViewVO orderOverView() {
        // 统计 “待接单”, “待派送”, “已完成”, “已取消” 和 总订单 数量
        Integer waitingOrders = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);
        Integer deliveredOrders = orderMapper.countStatus(Orders.CONFIRMED);
        Integer completedOrders = orderMapper.countStatus(Orders.COMPLETED);
        Integer cancelledOrders = orderMapper.countStatus(Orders.CANCELLED);
        Integer allOrders = orderMapper.countStatus(null);
        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    @Override
    public DishOverViewVO dishOverView() {
        // 统计 “在售”, “停售” 数量
        Integer sold = dishMapper.countStatus(StatusConstant.ENABLE);
        Integer discontinued = dishMapper.countStatus(StatusConstant.DISABLE);
        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    @Override
    public SetmealOverViewVO setmealOverView() {
        // 统计 “在售”, “停售” 数量
        Integer sold = setmealMapper.countStatus(StatusConstant.ENABLE);
        Integer discontinued = setmealMapper.countStatus(StatusConstant.DISABLE);
        return SetmealOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }


}
