package com.atguigu.controller.admin;

import com.atguigu.DTO.SetMealType.SetmealDTO;
import com.atguigu.DTO.SetMealType.SetmealPageQueryDTO;
import com.atguigu.VO.SetmealVO;
import com.atguigu.result.PageResult;
import com.atguigu.result.Result;
import com.atguigu.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Slf4j
@Tag(name = "套餐管理-控制层")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐：POST
     * @param setmealDTO （json）
     * @return Result<String>
     */
    @PostMapping
    @Operation(summary = "新增套餐")
    @CacheEvict(cacheNames = "setmealCache", key = "#setmealDTO.categoryId")
    public Result<String> save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}", setmealDTO);
        setmealService.save(setmealDTO);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 套餐分页查询：GET
     * @param setmealPageQueryDTO (不需要@RequestBody)
     * @return Result<PageResult>
     */
    @GetMapping("/page")
    @Operation(summary = "套餐分页查询")
    public Result<PageResult> page(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.page(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用套餐：POST
     * @param status （Integer）
     * @param id （Long）
     * @return Result<String>
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> startOrStop(@PathVariable Integer status,@RequestParam Long id){
        log.info("启用禁用套餐：id={}, status={}", id, status);
        setmealService.startOrStop(status, id);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 删除套餐：DELETE
     * @param  ids（List<Long>）
     * @return Result
     */
    @DeleteMapping
    @Operation(summary = "删除套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> delete(@RequestParam List<Long> ids){
        log.info("删除套餐：{}", ids);
        setmealService.deleteBatch(ids);

        // 缓存清理由 Spring Cache 自动处理
        return Result.success();
    }

    /**
     * 根据id查询套餐，用于修改页面回显数据
     * @param id （Long）
     * @return Result<SetmealVO>
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询套餐")
    public Result<SetmealVO> getById(@PathVariable Long id){
        log.info("根据id查询套餐：{}", id);
        SetmealVO setmealVO = setmealService.getByIdWithDishes(id);
        return Result.success(setmealVO);
    }

    /**
     * 修改套餐：PUT
     * @param setmealDTO （json）
     * @return Result<String>
     */
    @PutMapping
    @Operation(summary = "修改套餐")
    @CacheEvict(cacheNames = "setmealCache", allEntries = true)
    public Result<String> update(@RequestBody SetmealDTO setmealDTO){
        log.info("修改套餐：{}", setmealDTO);
        try {
            setmealService.updateInfo(setmealDTO);
            log.info("修改套餐成功，套餐ID：{}", setmealDTO.getId());

            // 缓存清理由 Spring Cache 自动处理
            return Result.success();
        } catch (Exception e) {
            log.error("修改套餐失败：", e);
            throw e;
        }
    }

}
