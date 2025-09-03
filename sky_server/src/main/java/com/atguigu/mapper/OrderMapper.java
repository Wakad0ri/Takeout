package com.atguigu.mapper;

import com.atguigu.DTO.OrderType.OrdersPageQueryDTO;
import com.atguigu.DTO.SaleType.GoodsSalesDTO;
import com.atguigu.Entity.Orders;
import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
@Tag(name = "订单管理Mapper")
public interface OrderMapper {


    /**
     * 插入订单数据
     * @param orders 订单数据
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单数据
     * @param outTradeNo 订单号
     * @return 订单数据
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String outTradeNo);

    /**
     * 更新订单数据
     * @param orders 订单数据
     */
    void update(Orders orders);

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO 分页查询参数
     * @return 订单数据
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单信息
     * @param id 订单id
     * @return Order 订单数据
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 根据状态统计订单数量
     * @param status 订单状态
     * @return 订单数量
     */
    @Select("select count(id) from orders where status = #{status}")

    Integer countStatus(Integer status);

    // 以下是SpringTask相关：

    /**
     * 根据订单状态和下单时间查询订单
     * @param status 待状态
     * @param localDateTime 下单时间
     * @return List<Orders>
     */
    @Select("select * from orders where status = #{status} and order_time < #{localDateTime}")  // 修改：将#{orderTime}改为#{localDateTime}与参数名匹配
    List<Orders> getByStatusAndOrderTimeLT(Integer status, LocalDateTime localDateTime);

    /**
     * 根据动态条件查询订单数据
     * @param map 查询条件
     * @return Orders
     */
    Double sumByMap(Map<String, Object> map);

    /**
     * 根据动态条件查询订单数量
     * @param map 查询条件
     * @return 订单数量
     */
    Integer getOrderCountByMap(Map<String, Object> map);

    /**
     * 查询销量排名top10
     * @param map 查询条件
     * @return 销量排名数据
     */
    List<GoodsSalesDTO> getTop10ByMap(Map<String, Object> map);
}
