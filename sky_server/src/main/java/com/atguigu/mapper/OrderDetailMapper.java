package com.atguigu.mapper;

import com.atguigu.Entity.OrderDetail;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@Tag(name = "订单明细管理Mapper")
public interface OrderDetailMapper {

    /**
     * 批量插入订单明细数据
     * @param orderDetails 订单明细数据
     */
    void insertBatch(List<OrderDetail> orderDetails);

    /**
     * 根据订单id查询订单明细
     * @param orderId 订单id
     * @return List<OrderDetail>
     */
    @Select("select * from order_detail where order_id = #{orderId}")
    List<OrderDetail> getByOrderId(Long orderId);
}
