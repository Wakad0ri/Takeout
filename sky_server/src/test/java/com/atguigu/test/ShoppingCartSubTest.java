package com.atguigu.test;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.ShoppingCart;
import com.atguigu.context.BaseContext;
import com.atguigu.mapper.ShoppingCartMapper;
import com.atguigu.service.ShoppingCartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 购物车减少数量功能测试
 * 测试购物车sub方法的各种场景
 */
//@SpringBootTest
@ActiveProfiles("test") // 使用测试配置
@Transactional // 测试后回滚数据
public class ShoppingCartSubTest {

//    @Autowired
    private ShoppingCartService shoppingCartService;

//    @Autowired
    private ShoppingCartMapper shoppingCartMapper;

    private static final Long TEST_USER_ID = 1L;
    private static final Long TEST_DISH_ID = 1L;

    @BeforeEach
    public void setUp() {
        // 设置当前用户ID
        BaseContext.setCurrentId(TEST_USER_ID);
    }

    /**
     * 测试减少购物车数量（数量大于1的情况）
     */
    @Test
    public void testSubShoppingCartWithMultipleItems() {
        // 准备测试数据：添加一个数量为3的购物车项
        ShoppingCart testCart = createTestShoppingCart(3);
        shoppingCartMapper.add(testCart);

        // 创建减少请求
        ShoppingCartDTO subRequest = new ShoppingCartDTO();
        subRequest.setDishId(TEST_DISH_ID);

        // 执行减少操作
        List<ShoppingCart> result = shoppingCartService.sub(subRequest);

        // 验证结果
        assert result != null : "返回结果不应为null";

        // 查询数据库验证数量是否正确减少
        ShoppingCart queryCart = new ShoppingCart();
        queryCart.setUserId(TEST_USER_ID);
        queryCart.setDishId(TEST_DISH_ID);
        List<ShoppingCart> cartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(queryCart);

        assert !cartList.isEmpty() : "购物车项应该仍然存在";
        assert cartList.get(0).getNumber() == 2 : "数量应该从3减少到2";

        System.out.println("测试通过：购物车数量从3减少到2");
    }

    /**
     * 测试减少购物车数量（数量等于1的情况，应该删除项目）
     */
    @Test
    public void testSubShoppingCartWithSingleItem() {
        // 准备测试数据：添加一个数量为1的购物车项
        ShoppingCart testCart = createTestShoppingCart(1);
        shoppingCartMapper.add(testCart);

        // 创建减少请求
        ShoppingCartDTO subRequest = new ShoppingCartDTO();
        subRequest.setDishId(TEST_DISH_ID);

        // 执行减少操作
        List<ShoppingCart> result = shoppingCartService.sub(subRequest);

        // 验证结果
        assert result != null : "返回结果不应为null";

        // 查询数据库验证项目是否被删除
        ShoppingCart queryCart = new ShoppingCart();
        queryCart.setUserId(TEST_USER_ID);
        queryCart.setDishId(TEST_DISH_ID);
        List<ShoppingCart> cartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(queryCart);

        assert cartList.isEmpty() : "购物车项应该被删除";

        System.out.println("测试通过：数量为1的购物车项被正确删除");
    }

    /**
     * 测试减少不存在的购物车项
     */
    @Test
    public void testSubNonExistentShoppingCartItem() {
        // 创建减少请求（针对不存在的商品）
        ShoppingCartDTO subRequest = new ShoppingCartDTO();
        subRequest.setDishId(999L); // 不存在的商品ID

        // 执行减少操作
        List<ShoppingCart> result = shoppingCartService.sub(subRequest);

        // 验证结果：应该正常返回，不会出错
        assert result != null : "返回结果不应为null";

        System.out.println("测试通过：减少不存在的购物车项不会出错");
    }

    /**
     * 测试带口味的购物车项减少
     */
    @Test
    public void testSubShoppingCartWithFlavor() {
        // 准备测试数据：添加一个带口味的购物车项
        ShoppingCart testCart = createTestShoppingCart(2);
        testCart.setDishFlavor("[{\"name\":\"辣度\",\"value\":\"微辣\"}]");
        shoppingCartMapper.add(testCart);

        // 创建减少请求
        ShoppingCartDTO subRequest = new ShoppingCartDTO();
        subRequest.setDishId(TEST_DISH_ID);
        subRequest.setDishFlavor("[{\"name\":\"辣度\",\"value\":\"微辣\"}]");

        // 执行减少操作
        List<ShoppingCart> result = shoppingCartService.sub(subRequest);

        // 验证结果
        assert result != null : "返回结果不应为null";

        // 查询数据库验证数量是否正确减少
        ShoppingCart queryCart = new ShoppingCart();
        queryCart.setUserId(TEST_USER_ID);
        queryCart.setDishId(TEST_DISH_ID);
        queryCart.setDishFlavor("[{\"name\":\"辣度\",\"value\":\"微辣\"}]");
        List<ShoppingCart> cartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(queryCart);

        assert !cartList.isEmpty() : "购物车项应该仍然存在";
        assert cartList.get(0).getNumber() == 1 : "数量应该从2减少到1";

        System.out.println("测试通过：带口味的购物车项数量正确减少");
    }

    /**
     * 创建测试用的购物车对象
     */
    private ShoppingCart createTestShoppingCart(Integer number) {
        ShoppingCart cart = new ShoppingCart();
        cart.setUserId(TEST_USER_ID);
        cart.setDishId(TEST_DISH_ID);
        cart.setName("测试菜品");
        cart.setImage("test.jpg");
        cart.setAmount(new BigDecimal("25.00"));
        cart.setNumber(number);
        cart.setCreateTime(LocalDateTime.now());
        return cart;
    }
}
