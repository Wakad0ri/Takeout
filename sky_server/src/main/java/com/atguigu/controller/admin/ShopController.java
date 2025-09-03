package com.atguigu.controller.admin;

import com.atguigu.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminRestController")
@RequestMapping("/admin/shop")
@Slf4j
@Tag(name = "店铺管理-服务层")
public class ShopController {

    @Autowired
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;


    /**
     * 店铺状态更改
     * @param status （Integer）
     * @return Result<String>
     */
    @PutMapping(value = "/{status}")
    @Operation(summary = "店铺状态更改")
    @SuppressWarnings("unchecked")
    public Result<String> setStatus(@PathVariable("status")Integer status){
        // 参数验证：状态只能是0或1
        if (status != 0 && status != 1) {
            return Result.error("状态参数错误，只能是0（打烊）或1（营业）");
        }
        log.info("店铺状态更改为：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     * @return Result<Integer>
     */
    @GetMapping("status")
    @Operation(summary = "获取店铺营业状态")
    @SuppressWarnings("all")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        // 如果Redis中没有设置状态，默认为打烊状态
        if (status == null) {
            status = 0;
        }
        log.info("获取店铺营业状态为：{}" , status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
package com.atguigu.controller.admin;

import com.atguigu.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminRestController")
@RequestMapping("/admin/shop")
@Slf4j
@Tag(name = "店铺管理-服务层")
public class ShopController {

    @Autowired
    @SuppressWarnings("rawtypes")
    private RedisTemplate redisTemplate;


    /**
     * 店铺状态更改
     * @param status （Integer）
     * @return Result<String>
     */
    @PutMapping(value = "/{status}")
    @Operation(summary = "店铺状态更改")
    @SuppressWarnings("unchecked")
    public Result<String> setStatus(@PathVariable("status")Integer status){
        // 参数验证：状态只能是0或1
        if (status != 0 && status != 1) {
            return Result.error("状态参数错误，只能是0（打烊）或1（营业）");
        }
        log.info("店铺状态更改为：{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);
        return Result.success();
    }

    /**
     * 获取店铺营业状态
     * @return Result<Integer>
     */
    @GetMapping("status")
    @Operation(summary = "获取店铺营业状态")
    @SuppressWarnings("all")
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        // 如果Redis中没有设置状态，默认为打烊状态
        if (status == null) {
            status = 0;
        }
        log.info("获取店铺营业状态为：{}" , status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
