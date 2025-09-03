package com.atguigu.controller.user;

import com.atguigu.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Tag(name = "店铺管理-用户服务层")
@Slf4j
public class ShopController {

    @SuppressWarnings("rawtypes")
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取营业状态
     * @return Result<Integer>
     */
    @GetMapping("status")
    @Operation(summary = "获取营业状态")
    @SuppressWarnings("all")
    public Result<Integer> getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        // 如果Redis中没有设置状态，默认为打烊状态
        if (status == null) {
            status = 0;
        }
        log.info("获取店铺营业状态为：{} " ,status == 1 ? "营业中" : "打烊中");
        return Result.success(status);
    }
}
