package com.atguigu.controller.user;

import com.atguigu.Entity.Setmeal;
import com.atguigu.VO.DishItemVO;
import com.atguigu.result.Result;
import com.atguigu.service.SetmealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userSetmealController")
@RequestMapping("/user/setmeal")
@Tag(name = "套餐管理-用户控制层")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据分类id查询套餐
     * @param categoryId 分类ID
     * @return Result<List<Setmeal>>
     */
    @GetMapping("/list")
    @Operation(summary = "根据分类id查询套餐")
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    public Result<List<Setmeal>> list(Long categoryId) {
        log.info("根据分类id查询套餐：{}", categoryId);

        // Spring Cache 会自动处理缓存逻辑：
        // 1. 先查询缓存，如果存在直接返回
        // 2. 如果不存在，执行方法并将结果缓存
        Setmeal setmeal = Setmeal.builder()
                .categoryId(categoryId)
                .status(1) // 只查询启用的套餐
                .build();
        List<Setmeal> list = setmealService.list(setmeal);
        return Result.success(list);
    }

    /**
     * 根据套餐id查询套餐详情
     * @param id 套餐ID
     * @return Result<List<DishItemVO>>
     */
    @GetMapping("/dish/{id}")
    @Operation(summary = "根据套餐id查询套餐详情")
    public Result<List<DishItemVO>> getSetmealDishesBySetmealId(@PathVariable("id") Long id) {
        log.info("根据套餐id查询套餐详情：{}", id);
        List<DishItemVO> dishItemVOList = setmealService.getDishItemVOListBySetmealId(id);
        return Result.success(dishItemVOList);
    }
}
