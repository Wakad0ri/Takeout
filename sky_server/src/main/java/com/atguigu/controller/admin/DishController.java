package com.atguigu.controller.admin;

import com.atguigu.DTO.DishType.DishDTO;
import com.atguigu.DTO.DishType.DishPageQueryDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.VO.DishVO;
import org.springframework.cache.annotation.CacheEvict;
import com.atguigu.result.PageResult;
import com.atguigu.result.Result;
import com.atguigu.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.redis.core.RedisTemplate; // 不再需要直接使用 RedisTemplate
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminDishController")
@RequestMapping("/admin/dish")
@Slf4j
@Tag(name = "菜品管理-控制层")
public class DishController {

    @Autowired
    private DishService dishService;

    // 移除 RedisTemplate，改用 AOP 处理缓存清理
    // @Autowired
    // @SuppressWarnings("rawtypes")
    // private RedisTemplate redisTemplate;

    /**
     * 新增菜品：POST
     * @param dishDTO （json）
     * @return  Result
     */
    @PostMapping
    @Operation(summary = "新增菜品")
    @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId")  // key 为 dishCache::#dishDTO.categoryId
    public Result<String> save(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 菜品分页查询：GET
     * @param dishPageQueryDTO （不需要@RequestBody）
     * @return Result<PageResult>
     */
    @GetMapping(value = "/page")
    @Operation(summary = "菜品分类分页查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分类分页查询：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用菜品：POST
     * @param status（Integer）,
     * @param id（Long）
     * @return Result<String>
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用菜品")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用菜品：{}", id);
        dishService.startOrStop(status, id);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 删除菜品：DELETE
     * @param  ids（List<Long>）
     * @return Result
     */
    @DeleteMapping
    @Operation(summary = "删除菜品")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result<String> deleteByIds(@RequestParam List<Long> ids){
        log.info("删除菜品：{}", ids);
        dishService.deleteBatch(ids);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 根据id查询菜品信息：GET
     * @param id （Long）
     * @return Result<DishVO>
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询菜品信息")
    public Result<DishVO> getById(@PathVariable("id") Long id) {
        log.info("根据id查询菜品信息：{}", id);
        DishVO dishVO = dishService.getByIdWithFlavors(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品：PUT
     * @param dishDTO （json）
     * @return Result
     */
    @PutMapping
    @Operation(summary = "更改菜品信息")
    @CacheEvict(cacheNames = "dishCache", allEntries = true)
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("更改菜品信息：{}", dishDTO);
        dishService.updateInfo(dishDTO);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 根据条件查询菜品：GET
     * @param categoryId 分类ID（可选）
     * @param name 菜品名称（可选，支持模糊查询）
     * @return Result<List<Dish>>
     * 在新增套餐时需要
     */
    @GetMapping("/list")
    @Operation(summary = "根据条件查询菜品")
    public Result<List<Dish>> list(Long categoryId, String name) {
        log.info("根据条件查询菜品：categoryId={}, name={}", categoryId, name);
        List<Dish> list = dishService.list(categoryId, name);
        return Result.success(list);
    }

    // cleanCache 方法已移除，缓存清理由 AOP 自动处理 -> 后用Spring Cache处理

}
