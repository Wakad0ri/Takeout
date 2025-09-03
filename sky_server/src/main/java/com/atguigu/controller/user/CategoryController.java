package com.atguigu.controller.user;

import com.atguigu.Entity.Category;
import com.atguigu.result.Result;
import com.atguigu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Tag(name = "分类管理-用户控制层")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类型查询分类列表
     * @param type 分类类型（1 菜品分类 2 套餐分类，不传则查询所有）
     * @return Result<List<Category>>
     */
    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类列表")
    public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询分类列表：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
package com.atguigu.controller.user;

import com.atguigu.Entity.Category;
import com.atguigu.result.Result;
import com.atguigu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("userCategoryController")
@RequestMapping("/user/category")
@Tag(name = "分类管理-用户控制层")
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 根据类型查询分类列表
     * @param type 分类类型（1 菜品分类 2 套餐分类，不传则查询所有）
     * @return Result<List<Category>>
     */
    @GetMapping("/list")
    @Operation(summary = "根据类型查询分类列表")
    public Result<List<Category>> list(Integer type) {
        log.info("根据类型查询分类列表：{}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

}
