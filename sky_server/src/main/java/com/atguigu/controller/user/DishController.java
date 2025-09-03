package com.atguigu.controller.user;

import com.atguigu.Entity.Dish;
import com.atguigu.VO.DishVO;
import com.atguigu.constant.StatusConstant;
import com.atguigu.result.Result;
import com.atguigu.service.DishService;
// import org.springframework.cache.annotation.Cacheable; // 暂时禁用缓存
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate; // 不再需要直接使用 RedisTemplate
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Tag(name = "菜品管理-用户控制层")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    // 移除 RedisTemplate，改用 Spring Cache
    // @Autowired
    // private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品信息封装VO：GET
     * @param categoryId 分类ID（Long）
     * @return Result<List<DishVO>>
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品：{}", categoryId);

        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)  // 只查询启用的菜品
                .build();
        List<DishVO> list = dishService.getDishVOListByDish(dish);

        return Result.success(list);
    }
}
package com.atguigu.controller.user;

import com.atguigu.Entity.Dish;
import com.atguigu.VO.DishVO;
import com.atguigu.constant.StatusConstant;
import com.atguigu.result.Result;
import com.atguigu.service.DishService;
// import org.springframework.cache.annotation.Cacheable; // 暂时禁用缓存
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate; // 不再需要直接使用 RedisTemplate
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userDishController")
@RequestMapping("/user/dish")
@Tag(name = "菜品管理-用户控制层")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    // 移除 RedisTemplate，改用 Spring Cache
    // @Autowired
    // private RedisTemplate redisTemplate;

    /**
     * 根据分类id查询菜品信息封装VO：GET
     * @param categoryId 分类ID（Long）
     * @return Result<List<DishVO>>
     */
    @GetMapping("/list")
    public Result<List<DishVO>> list(Long categoryId) {
        log.info("根据分类id查询菜品：{}", categoryId);

        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)  // 只查询启用的菜品
                .build();
        List<DishVO> list = dishService.getDishVOListByDish(dish);

        return Result.success(list);
    }
}
