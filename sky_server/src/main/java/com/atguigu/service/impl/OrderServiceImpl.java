package com.atguigu.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.atguigu.DTO.OrderType.*;
import com.atguigu.Entity.*;
import com.atguigu.VO.OrderPaymentVO;
import com.atguigu.VO.OrderStatisticsVO;
import com.atguigu.VO.OrderSubmitVO;
import com.atguigu.VO.OrderVO;
import com.atguigu.constant.MessageConstant;
import com.atguigu.context.BaseContext;
import com.atguigu.exception.AddressBusinessException;
import com.atguigu.exception.OrderBusinessException;
import com.atguigu.exception.ShoppingCartBusinessException;
import com.atguigu.mapper.*;
import com.atguigu.result.PageResult;
import com.atguigu.service.OrderService;
import com.atguigu.utils.HttpClientUtil;
import com.atguigu.utils.WeChatPayUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service("orderService")
@Transactional
@Tag(name = "用户端订单服务")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeChatPayUtil weChatPayUtil;

    /**
     * 用户下单
     * @param ordersSubmitDTO 订单信息
     * @return OrderSubmitVO
     */
    @Override
    public OrderSubmitVO submit(OrdersSubmitDTO ordersSubmitDTO) {
        // 处理各种业务异常（地址簿为空，购物车为空）
        Address address = addressBookMapper.getById(ordersSubmitDTO.getAddressBookId());
        if (address == null) {
            throw new AddressBusinessException(MessageConstant.ADDRESS_BOOK_IS_NULL);
        }

        // 检查配送范围
        checkOutOfRange(address.getCityName() + address.getDistrictName() + address.getDetail());

        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        if (list == null || list.isEmpty()){
            throw new ShoppingCartBusinessException(MessageConstant.SHOPPING_CART_IS_NULL);
        }

        // 向订单表插入1条数据
        Orders orders = Orders.builder()
                .userId(userId)
                .orderTime(LocalDateTime.now())
                .payStatus(Orders.UN_PAID)
                .status(Orders.PENDING_PAYMENT)
                .number(String.valueOf(System.currentTimeMillis()))   // 订单号，使用当前时间戳
                .phone(address.getPhone())    // 手机号，在之前就已经获得的地址簿中获得
                .consignee(address.getConsignee())    // 收货人，在之前就已经获得的地址簿中获得
                .build();
        // 复制DTO中的其他属性
        BeanUtils.copyProperties(ordersSubmitDTO, orders);
        orderMapper.insert(orders); // 要设置useGeneratedKeys=true和keyProperty=id

        // 向订单明细表插入n条数据
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (ShoppingCart cart : list){
            OrderDetail orderDetail = new OrderDetail();
            // 将购物车数据复制到订单明细
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setOrderId(orders.getId());
            orderDetails.add(orderDetail);
        }
        orderDetailMapper.insertBatch(orderDetails);

        // 清空该用户购物车数据
        shoppingCartMapper.clean(shoppingCart);

        // 封装VO返回结果
        return OrderSubmitVO.builder()
                .id(orders.getId())
                .orderNumber(orders.getNumber())
                .orderAmount(orders.getAmount())
                .orderTime(orders.getOrderTime())
                .build();
    }

    /**
     * 用户支付
     * @param ordersPaymentDTO 订单支付信息
     * @return OrderPaymentVO
     */
    @Override
    public OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception {
        // 登录当前用户id
        Long userId = BaseContext.getCurrentId();
        User user = userMapper.getById(userId);

        // 调用微信支付接口，生成预支付交易单
        JSONObject jsonObject = weChatPayUtil.pay(
                ordersPaymentDTO.getOrderNumber(),  // 订单号
                new BigDecimal("0.01"), // 金额，固定
                "苍穹外卖订单",   // 订单描述
                user.getOpenid()    // 微信用户的openid
        );

        if (jsonObject.getString("code") != null && jsonObject.getString("code").equals("ORDERPAID")){
            throw new OrderBusinessException("该订单已支付");
        }

        OrderPaymentVO vo = jsonObject.toJavaObject(OrderPaymentVO.class);
        vo.setPackageStr(jsonObject.getString("package"));
        return vo;
    }

    /**
     * 支付成功，修改订单状态
     *
     * @param number 微信支付成功返回的订单号
     */
    @Override
    public void paySuccess(String number) {

        // 根据订单号查询订单
        Orders GetOrder = orderMapper.getByNumber(number);

        // 根据订单id更新订单的状态、支付方式、支付状态、结账时间
        Orders orders = Orders.builder()
                .id(GetOrder.getId())   // 订单id
                .status(Orders.TO_BE_CONFIRMED) // 待派送
                .payStatus(Orders.PAID) // 已支付
                .checkoutTime(LocalDateTime.now())  // 结账时间
                .build();

        orderMapper.update(orders);
    }

    /**
     * 历史订单查询
     * @param orderHistoryQueryDTO 历史订单查询参数
     * @return PageResult
     */
    @Override
    public PageResult historyOrders(OrderHistoryQueryDTO orderHistoryQueryDTO) {
        // 历史订单分页查询设置：
        PageHelper.startPage(orderHistoryQueryDTO.getPage(), orderHistoryQueryDTO.getPageSize());
        OrdersPageQueryDTO ordersPageQueryDTO = new OrdersPageQueryDTO();
        BeanUtils.copyProperties(orderHistoryQueryDTO, ordersPageQueryDTO);
        // 为什么要设置UserId？
        // 因为要一并获取订单明细表分装到VO里，而订单表和订单明细表是多对多的关系，所以要查询订单表和订单明细表，要设置UserId
        ordersPageQueryDTO.setUserId(BaseContext.getCurrentId());

        Page<Orders> GetPageOrders = orderMapper.pageQuery(ordersPageQueryDTO);
        List<OrderVO> list = new ArrayList<>();
        if (GetPageOrders != null && !GetPageOrders.isEmpty()) {
            for (Orders orders : GetPageOrders) {
                Long orderId = orders.getId();// 订单id

                // 查询订单明细
                List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);

                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                orderVO.setOrderDetailList(orderDetails);

                list.add(orderVO);
            }
        }
        return new PageResult(GetPageOrders.getTotal(), list);
    }

    /**
     * 取消订单
     * @param id （Long）
     */
    @Override
    public void cancel(Long id) {
        // 查询当前订单状态
        Orders ordersDB = orderMapper.getById(id);
        Integer Status = ordersDB.getStatus();
        if (Status == null){
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        // 若订单状态不是待接单、待派送、待接单，则不能取消
        if (Status > 2){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 订单状态正常，则取消订单
        if (Status.equals(Orders.TO_BE_CONFIRMED)){
            // 调用微信支付接口，申请退款
            try {
                weChatPayUtil.refund(
                        ordersDB.getNumber(), // 订单号
                        ordersDB.getNumber(), // 退款订单号
                        new BigDecimal("0.01"), // 退款金额
                        new BigDecimal("0.01")  // 原金额
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            // 修改支付状态：退款
            ordersDB.setPayStatus(Orders.REFUND);

        }
        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.CANCELLED)
                .cancelReason("用户取消")
                .cancelTime(LocalDateTime.now())
                .build();
        orderMapper.update(orders);
    }

    /**
     * 再来一单
     * @param orderId （Long）
     */
    @Override
    public void again(Long orderId) {
        // 获取当前用户id
        Long userId = BaseContext.getCurrentId();
        // 根据订单id检索订单详情信息
        List<OrderDetail> details = orderDetailMapper.getByOrderId(orderId);

        // 将订单详情对象转换为购物车对象
        List<ShoppingCart> carts = details.stream().map(x -> {
            ShoppingCart cart = new ShoppingCart();
            BeanUtils.copyProperties(x, cart);
            cart.setUserId(userId);
            cart.setCreateTime(LocalDateTime.now());
            return cart;
        }).toList();
        shoppingCartMapper.insertBatch(carts);
    }

    // 以下是管理端：

    /**
     * 订单分页查询
     * @param ordersPageQueryDTO 订单分页查询参数
     * @return PageResult
     * 子方法：
     *   - 将page<Orders>的数据转换成List<OrderVO>
     *   - 根据订单id获取菜品信息字符串
     */
    @Override
    public PageResult page(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageHelper.startPage(ordersPageQueryDTO.getPage(), ordersPageQueryDTO.getPageSize());
        Page<Orders> page = orderMapper.pageQuery(ordersPageQueryDTO);
        long total = page.getTotal();
//        List<Orders> records = page.getResult();
//        return new PageResult(total, records);
        // 但是这个地方需要封装成VO给前端，为什么？
        // 答：如果只返回Orders对象，管理员只能看到订单号、总金额等基本信息，无法知道具体需要准备哪些菜品。
        // 通过转换为OrderVO并添加菜品信息，管理员可以直接看到"宫保鸡丁3；麻婆豆腐1"这样的菜品明细。
        List<OrderVO> VOs = getOrderVOList(page);
        return new PageResult(total, VOs);
    }
    private List<OrderVO> getOrderVOList(Page<Orders> page) {
        // 定义目标列表，从page.getResult中获取records
        List<OrderVO> list = new ArrayList<>();
        List<Orders> records = page.getResult();

        if (records != null && !records.isEmpty()) {
            for (Orders orders : records) {
                // 将共同字段复制到OrderVO
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(orders, orderVO);
                // 根据订单信息填充菜品信息字符串，封装VO后插入目标列表
                String orderDishes = getOrderDishesStr(orders.getId());
                orderVO.setOrderDishes(orderDishes);
                list.add(orderVO);
            }
        }
        return list;
    }
    private String getOrderDishesStr(Long orderId) {
        // 根据订单id获取订单详情
        List<OrderDetail> orderDetails = orderDetailMapper.getByOrderId(orderId);
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new OrderBusinessException(MessageConstant.ORDER_DETAIL_NOT_FOUND);
        }
        // 将每一条订单菜品信息拼接为字符串（格式：宫保鸡丁*3; ）
        List<String> orderDishList = orderDetails.stream()
                .map(x -> x.getName() + "*" + x.getNumber() + "; ")
                .toList();
        return String.join("；", orderDishList);
    }

    /**
     * 订单状态统计
     * @return OrderStatisticsVO
     */
    @Override
    public OrderStatisticsVO statistics() {
        // 根据状态查询三种订单数量
        Integer waitConfirmCount = orderMapper.countStatus(Orders.TO_BE_CONFIRMED);   // 待接单
        Integer waitSendCount = orderMapper.countStatus(Orders.CONFIRMED); //待派送
        Integer waitPayCount = orderMapper.countStatus(Orders.DELIVERY_IN_PROGRESS);   // 派送中

        // 封装结果并返回
        return OrderStatisticsVO.builder()
                .toBeConfirmed(waitConfirmCount)
                .confirmed(waitSendCount)
                .deliveryInProgress(waitPayCount)
                .build();
    }

    /**
     * 订单详情展示
     * @param id （Long）
     * @return OrderVO
     */
    @Override
    public OrderVO details(Long id) {
        // 根据id查询订单，根据订单id查询订单详情
        Orders ordersDB = orderMapper.getById(id);
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        List<OrderDetail> orderDetailList = orderDetailMapper.getByOrderId(id);
        // 封装成OrderVO并返回
        OrderVO orderVO = new OrderVO();
        BeanUtils.copyProperties(ordersDB, orderVO);
        orderVO.setOrderDetailList(orderDetailList);
        return orderVO;
    }

    /**
     * 接单
     * @param ordersConfirmDTO 接单参数
     */
    @Override
    public void confirm(OrdersConfirmDTO ordersConfirmDTO) {
        Orders orders = Orders.builder()
                .id(ordersConfirmDTO.getId())
                .status(Orders.CONFIRMED)
                .build();
        orderMapper.update(orders);
    }

    /**
     * 拒单
     * @param ordersRejectionDTO 拒单参数
     */
    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        // 根据id查询订单，处理业务逻辑错误
        Orders ordersDB = orderMapper.getById(ordersRejectionDTO.getId());
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (ordersDB.getStatus() > Orders.TO_BE_CONFIRMED){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 若用户已支付，需要退款
        Integer payStatus = ordersDB.getPayStatus();
        if (Objects.equals(payStatus, Orders.PAID)){
            String refund = weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal("0.01"),
                    new BigDecimal("0.01"));
            log.info("申请退款：{}", refund);
        }
        // 更新订单状态、拒单原因
        Orders orders = Orders.builder()
                .id(ordersRejectionDTO.getId())
                .status(Orders.CANCELLED)
                .rejectionReason(ordersRejectionDTO.getRejectionReason())
                .cancelTime(LocalDateTime.now())
                .build();
        orderMapper.update(orders);
    }

    /**
     * 取消订单
     * @param ordersCancelDTO 取消订单参数
     */
    @Override
    public void cancel(OrdersCancelDTO ordersCancelDTO) throws Exception {
        // 根据id查询订单，处理业务逻辑错误
        Orders ordersDB = orderMapper.getById(ordersCancelDTO.getId());
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (ordersDB.getStatus() > Orders.TO_BE_CONFIRMED){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        if (Objects.equals(ordersDB.getPayStatus(), Orders.PAID)){
            // 用户已支付，需要退款
            String refund = weChatPayUtil.refund(
                    ordersDB.getNumber(),
                    ordersDB.getNumber(),
                    new BigDecimal("0.01"),
                    new BigDecimal("0.01"));
            log.info("申请退款：{}", refund);
        }
        // 更新订单状态、取消原因
        Orders orders = Orders.builder()
                .id(ordersCancelDTO.getId())
                .status(Orders.CANCELLED)
                .cancelReason(ordersCancelDTO.getCancelReason())
                .cancelTime(LocalDateTime.now())
                .build();
        orderMapper.update(orders);
    }

    /**
     * 派送订单
     * @param id （Long）
     */
    @Override
    public void delivery(Long id) {
        // 根据id查询订单，处理业务逻辑错误
        Orders ordersDB = orderMapper.getById(id);
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (!Objects.equals(ordersDB.getStatus(), Orders.CONFIRMED)){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 更新订单状态
        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.DELIVERY_IN_PROGRESS)
                .build();
        orderMapper.update(orders);
    }

    /**
     * 完成订单
     * @param id （Long）
     */
    @Override
    public void complete(Long id) {
        // 根据id查询订单，处理业务逻辑错误
        Orders ordersDB = orderMapper.getById(id);
        if (ordersDB == null) {
            throw new OrderBusinessException(MessageConstant.ORDER_NOT_FOUND);
        }
        if (!Objects.equals(ordersDB.getStatus(), Orders.DELIVERY_IN_PROGRESS)){
            throw new OrderBusinessException(MessageConstant.ORDER_STATUS_ERROR);
        }
        // 创建更新对象
        Orders orders = Orders.builder()
                .id(id)
                .status(Orders.COMPLETED)
                .deliveryTime(LocalDateTime.now())
                .build();
        orderMapper.update(orders);
    }



    // 以下是百度地图链接APi设置
    @Value("${sky.shop.address}")   // 导入的Value注解是springboot提供的
    private String shopAddress;
    @Value("${sky.baidu.ak}")
    private String ak;
    /**
     * 检查客户的收货地址是否超出配送范围
     * @param address 客户收货地址
     */
    private void checkOutOfRange(String address) {
        // 调用百度地图API获取店铺和客户地址的距离
        Map map = new HashMap();
        map.put("address",shopAddress);
        map.put("output","json");
        map.put("ak",ak);

        //获取店铺的经纬度坐标
        String shopCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        JSONObject jsonObject = JSON.parseObject(shopCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("店铺地址解析失败");
        }

        //数据解析
        JSONObject location = jsonObject.getJSONObject("result").getJSONObject("location");
        String lat = location.getString("lat");
        String lng = location.getString("lng");
        //店铺经纬度坐标 (经度,纬度)
        String shopLngLat = lng + "," + lat;

        map.put("address",address);
        //获取用户收货地址的经纬度坐标
        String userCoordinate = HttpClientUtil.doGet("https://api.map.baidu.com/geocoding/v3", map);

        jsonObject = JSON.parseObject(userCoordinate);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("收货地址解析失败");
        }

        //数据解析
        location = jsonObject.getJSONObject("result").getJSONObject("location");
        lat = location.getString("lat");
        lng = location.getString("lng");
        //用户收货地址经纬度坐标 (经度,纬度)
        String userLngLat = lng + "," + lat;

        map.put("origin",shopLngLat);
        map.put("destination",userLngLat);
        map.put("steps_info","0");

        //路线规划
        String json = HttpClientUtil.doGet("https://api.map.baidu.com/directionlite/v1/driving", map);

        jsonObject = JSON.parseObject(json);
        if(!jsonObject.getString("status").equals("0")){
            throw new OrderBusinessException("配送路线规划失败");
        }

        //数据解析
        JSONObject result = jsonObject.getJSONObject("result");
        JSONArray jsonArray = (JSONArray) result.get("routes");
        Integer distance = (Integer) ((JSONObject) jsonArray.get(0)).get("distance");

        if(distance > 5000){
            //配送距离超过5000米
            throw new OrderBusinessException("超出配送范围");
        }
    }
}

