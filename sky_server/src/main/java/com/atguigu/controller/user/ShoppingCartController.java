package com.atguigu.controller.user;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.ShoppingCart;
import com.atguigu.result.Result;
import com.atguigu.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "用户端-购物车管理")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车： POST
     * @param shoppingCartDTO （json）
     * @return result<String>
     */
    @PostMapping("/add")
    @Operation(summary = "添加购物车")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 修复购物车口味数据：PUT
     * 解决用户选择口味顺序不同导致的重复添加问题（当前进度不需要）
     * @return result<String>
     */
    @PutMapping("/fix-flavors")
    @Operation(summary = "修复购物车口味数据")
    public Result<String> fixCartFlavors() {
        log.info("开始修复购物车口味数据");
        try {
            shoppingCartService.normalizeExistingCartFlavors();
            return Result.success("购物车口味数据修复成功");
        } catch (Exception e) {
            log.error("购物车口味数据修复失败", e);
            return Result.error("购物车口味数据修复失败: " + e.getMessage());
        }
    }

    /**
     * 查看购物车： GET
     * @return result<List<ShoppingCart>>
     */
    @GetMapping("/list")
    @Operation(summary = "查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * 清空购物车： DELETE
     * @return result<String>
     */
    @DeleteMapping("/clean")
    @Operation(summary = "清空购物车")
    public Result<String> clean() {
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }

    /**
     * 减少购物车商品数量： POST
     * 功能说明：
     * - 如果商品数量大于1，则数量减1
     * - 如果商品数量等于1，则删除该购物车项
     * - 支持菜品和套餐，支持口味区分
     * @param shoppingCartDTO 包含要减少的商品信息（dishId或setmealId，可选dishFlavor）
     * @return result<List<ShoppingCart>> 返回更新后的购物车列表
     */
    @PostMapping("/sub")
    @Operation(summary = "减少购物车商品数量")
    public Result<List<ShoppingCart>> sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("减少购物车商品数量：{}", shoppingCartDTO);
        List<ShoppingCart> list = shoppingCartService.sub(shoppingCartDTO);
        return Result.success(list);
    }


}
package com.atguigu.controller.user;

import com.atguigu.DTO.ShoppingCartType.ShoppingCartDTO;
import com.atguigu.Entity.ShoppingCart;
import com.atguigu.result.Result;
import com.atguigu.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Tag(name = "用户端-购物车管理")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车： POST
     * @param shoppingCartDTO （json）
     * @return result<String>
     */
    @PostMapping("/add")
    @Operation(summary = "添加购物车")
    public Result<String> add(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("添加购物车：{}", shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * 修复购物车口味数据：PUT
     * 解决用户选择口味顺序不同导致的重复添加问题（当前进度不需要）
     * @return result<String>
     */
    @PutMapping("/fix-flavors")
    @Operation(summary = "修复购物车口味数据")
    public Result<String> fixCartFlavors() {
        log.info("开始修复购物车口味数据");
        try {
            shoppingCartService.normalizeExistingCartFlavors();
            return Result.success("购物车口味数据修复成功");
        } catch (Exception e) {
            log.error("购物车口味数据修复失败", e);
            return Result.error("购物车口味数据修复失败: " + e.getMessage());
        }
    }

    /**
     * 查看购物车： GET
     * @return result<List<ShoppingCart>>
     */
    @GetMapping("/list")
    @Operation(summary = "查看购物车")
    public Result<List<ShoppingCart>> list() {
        log.info("查看购物车");
        List<ShoppingCart> list = shoppingCartService.list();
        return Result.success(list);
    }

    /**
     * 清空购物车： DELETE
     * @return result<String>
     */
    @DeleteMapping("/clean")
    @Operation(summary = "清空购物车")
    public Result<String> clean() {
        log.info("清空购物车");
        shoppingCartService.clean();
        return Result.success();
    }

    /**
     * 减少购物车商品数量： POST
     * 功能说明：
     * - 如果商品数量大于1，则数量减1
     * - 如果商品数量等于1，则删除该购物车项
     * - 支持菜品和套餐，支持口味区分
     * @param shoppingCartDTO 包含要减少的商品信息（dishId或setmealId，可选dishFlavor）
     * @return result<List<ShoppingCart>> 返回更新后的购物车列表
     */
    @PostMapping("/sub")
    @Operation(summary = "减少购物车商品数量")
    public Result<List<ShoppingCart>> sub(@RequestBody ShoppingCartDTO shoppingCartDTO) {
        log.info("减少购物车商品数量：{}", shoppingCartDTO);
        List<ShoppingCart> list = shoppingCartService.sub(shoppingCartDTO);
        return Result.success(list);
    }


}
