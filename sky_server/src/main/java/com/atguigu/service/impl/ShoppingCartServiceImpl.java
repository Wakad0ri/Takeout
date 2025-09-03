package com.atguigu.service.impl;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.Entity.Setmeal;
import com.atguigu.Entity.ShoppingCart;
import com.atguigu.context.BaseContext;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.SetmealMapper;
import com.atguigu.mapper.ShoppingCartMapper;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.utils.DishFlavorNormalizer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Tag(name = "购物车服务实现类")
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO （json）
     */
    @Override
    @Transactional
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前添加的菜品或套餐是否在购物车中（因为DTO没有userId，所以封装到ShoppingCart来查询）
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 对口味进行标准化处理，解决选择顺序不同导致的问题（目前进度不需要进行掌握）
        if (shoppingCart.getDishFlavor() != null && !shoppingCart.getDishFlavor().trim().isEmpty()) {
            String normalizedFlavor = DishFlavorNormalizer.normalize(shoppingCart.getDishFlavor());
            shoppingCart.setDishFlavor(normalizedFlavor);
        }

        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(shoppingCart);
        if (shoppingCartList != null && !shoppingCartList.isEmpty()){
            ShoppingCart cart = shoppingCartList.get(0);    // 获取购物车项
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            // 判断本次添加的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null){
                Dish dish = dishMapper.getById(dishId);

                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
                } else{
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal= setmealMapper.getById(setmealId);

                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.add(shoppingCart);

        }
    }

    /**
     * 标准化所有购物车项的口味数据
     * 用于修复现有数据中口味顺序不一致的问题
     */
    @Override
    public void normalizeExistingCartFlavors() {
        try {
            // 查询所有有口味的购物车项
            List<ShoppingCart> cartItems = shoppingCartMapper.getAllCartItemsWithFlavors();

            log.info("开始标准化购物车口味数据，共{}个项目", cartItems.size());

            int updatedCount = 0;
            for (ShoppingCart item : cartItems) {
                String originalFlavor = item.getDishFlavor();
                String normalizedFlavor = DishFlavorNormalizer.normalize(originalFlavor);

                // 只有当标准化后的结果与原始数据不同时才更新
                if (!normalizedFlavor.equals(originalFlavor)) {
                    item.setDishFlavor(normalizedFlavor);
                    shoppingCartMapper.updateFlavorById(item);
                    updatedCount++;
                    log.debug("更新购物车项ID: {}, 原口味: {}, 新口味: {}",
                             item.getId(), originalFlavor, normalizedFlavor);
                }
            }

            log.info("购物车口味数据标准化完成，更新了{}个项目", updatedCount);

        } catch (Exception e) {
            log.error("购物车口味数据标准化失败", e);
            throw new RuntimeException("购物车数据修复失败", e);
        }
    }

    /**
     * 获取购物车列表
     * @return List<ShoppingCart>
     */
    @Override
    public List<ShoppingCart> list(){
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean(){
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        shoppingCartMapper.clean(shoppingCart);
    }

    /**
     * 减少购物车商品数量
     * 业务逻辑：
     * 1. 根据用户ID、商品ID（菜品或套餐）、口味查找购物车项
     * 2. 如果找到且数量大于1，则数量减1
     * 3. 如果找到且数量等于1，则删除该购物车项
     * 4. 如果未找到，则不做任何操作
     * 5. 返回更新后的完整购物车列表
     * @param shoppingCartDTO 包含要减少的商品信息（dishId或setmealId，可选dishFlavor）
     * @return 更新后的购物车列表
     */
    @Override
    @Transactional
    public List<ShoppingCart> sub(ShoppingCartDTO shoppingCartDTO) {
        // 构建查询条件，查找对应的购物车项
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 对口味进行标准化处理，确保查询一致性
        if (shoppingCart.getDishFlavor() != null && !shoppingCart.getDishFlavor().trim().isEmpty()) {
            String normalizedFlavor = DishFlavorNormalizer.normalize(shoppingCart.getDishFlavor());
            shoppingCart.setDishFlavor(normalizedFlavor);
        }

        // 查询购物车中是否存在该商品
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(shoppingCart);

        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
            ShoppingCart cart = shoppingCartList.get(0);
            Integer currentNumber = cart.getNumber();

            if (currentNumber > 1) {
                // 如果数量大于1，则减1
                cart.setNumber(currentNumber - 1);
                shoppingCartMapper.updateNumberById(cart);
            } else {
                // 如果数量等于1，则删除该购物车项
                shoppingCartMapper.deleteById(cart.getId());
            }
        }

        // 返回更新后的购物车列表
        return list();
    }
}
package com.atguigu.service.impl;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.Entity.Setmeal;
import com.atguigu.Entity.ShoppingCart;
import com.atguigu.context.BaseContext;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.SetmealMapper;
import com.atguigu.mapper.ShoppingCartMapper;
import com.atguigu.service.ShoppingCartService;
import com.atguigu.utils.DishFlavorNormalizer;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Tag(name = "购物车服务实现类")
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 添加购物车
     * @param shoppingCartDTO （json）
     */
    @Override
    @Transactional
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        // 判断当前添加的菜品或套餐是否在购物车中（因为DTO没有userId，所以封装到ShoppingCart来查询）
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 对口味进行标准化处理，解决选择顺序不同导致的问题（目前进度不需要进行掌握）
        if (shoppingCart.getDishFlavor() != null && !shoppingCart.getDishFlavor().trim().isEmpty()) {
            String normalizedFlavor = DishFlavorNormalizer.normalize(shoppingCart.getDishFlavor());
            shoppingCart.setDishFlavor(normalizedFlavor);
        }

        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(shoppingCart);
        if (shoppingCartList != null && !shoppingCartList.isEmpty()){
            ShoppingCart cart = shoppingCartList.get(0);    // 获取购物车项
            cart.setNumber(cart.getNumber() + 1);
            shoppingCartMapper.updateNumberById(cart);
        } else {
            // 判断本次添加的是菜品还是套餐
            Long dishId = shoppingCartDTO.getDishId();
            if (dishId != null){
                Dish dish = dishMapper.getById(dishId);

                shoppingCart.setName(dish.getName());
                shoppingCart.setImage(dish.getImage());
                shoppingCart.setAmount(dish.getPrice());
                } else{
                Long setmealId = shoppingCartDTO.getSetmealId();
                Setmeal setmeal= setmealMapper.getById(setmealId);

                shoppingCart.setName(setmeal.getName());
                shoppingCart.setImage(setmeal.getImage());
                shoppingCart.setAmount(setmeal.getPrice());
            }
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());

            shoppingCartMapper.add(shoppingCart);

        }
    }

    /**
     * 标准化所有购物车项的口味数据
     * 用于修复现有数据中口味顺序不一致的问题
     */
    @Override
    public void normalizeExistingCartFlavors() {
        try {
            // 查询所有有口味的购物车项
            List<ShoppingCart> cartItems = shoppingCartMapper.getAllCartItemsWithFlavors();

            log.info("开始标准化购物车口味数据，共{}个项目", cartItems.size());

            int updatedCount = 0;
            for (ShoppingCart item : cartItems) {
                String originalFlavor = item.getDishFlavor();
                String normalizedFlavor = DishFlavorNormalizer.normalize(originalFlavor);

                // 只有当标准化后的结果与原始数据不同时才更新
                if (!normalizedFlavor.equals(originalFlavor)) {
                    item.setDishFlavor(normalizedFlavor);
                    shoppingCartMapper.updateFlavorById(item);
                    updatedCount++;
                    log.debug("更新购物车项ID: {}, 原口味: {}, 新口味: {}",
                             item.getId(), originalFlavor, normalizedFlavor);
                }
            }

            log.info("购物车口味数据标准化完成，更新了{}个项目", updatedCount);

        } catch (Exception e) {
            log.error("购物车口味数据标准化失败", e);
            throw new RuntimeException("购物车数据修复失败", e);
        }
    }

    /**
     * 获取购物车列表
     * @return List<ShoppingCart>
     */
    @Override
    public List<ShoppingCart> list(){
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        return shoppingCartMapper.list(shoppingCart);
    }

    /**
     * 清空购物车
     */
    @Override
    public void clean(){
        Long userId = BaseContext.getCurrentId();
        ShoppingCart shoppingCart = ShoppingCart.builder()
                .userId(userId)
                .build();
        shoppingCartMapper.clean(shoppingCart);
    }

    /**
     * 减少购物车商品数量
     * 业务逻辑：
     * 1. 根据用户ID、商品ID（菜品或套餐）、口味查找购物车项
     * 2. 如果找到且数量大于1，则数量减1
     * 3. 如果找到且数量等于1，则删除该购物车项
     * 4. 如果未找到，则不做任何操作
     * 5. 返回更新后的完整购物车列表
     * @param shoppingCartDTO 包含要减少的商品信息（dishId或setmealId，可选dishFlavor）
     * @return 更新后的购物车列表
     */
    @Override
    @Transactional
    public List<ShoppingCart> sub(ShoppingCartDTO shoppingCartDTO) {
        // 构建查询条件，查找对应的购物车项
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO, shoppingCart);
        shoppingCart.setUserId(BaseContext.getCurrentId());

        // 对口味进行标准化处理，确保查询一致性
        if (shoppingCart.getDishFlavor() != null && !shoppingCart.getDishFlavor().trim().isEmpty()) {
            String normalizedFlavor = DishFlavorNormalizer.normalize(shoppingCart.getDishFlavor());
            shoppingCart.setDishFlavor(normalizedFlavor);
        }

        // 查询购物车中是否存在该商品
        List<ShoppingCart> shoppingCartList = shoppingCartMapper.getListByUserId_DishId_SetmealId_DishFlavor(shoppingCart);

        if (shoppingCartList != null && !shoppingCartList.isEmpty()) {
            ShoppingCart cart = shoppingCartList.get(0);
            Integer currentNumber = cart.getNumber();

            if (currentNumber > 1) {
                // 如果数量大于1，则减1
                cart.setNumber(currentNumber - 1);
                shoppingCartMapper.updateNumberById(cart);
            } else {
                // 如果数量等于1，则删除该购物车项
                shoppingCartMapper.deleteById(cart.getId());
            }
        }

        // 返回更新后的购物车列表
        return list();
    }
}
