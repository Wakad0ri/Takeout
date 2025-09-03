package com.atguigu.controller.admin;

import com.atguigu.DTO.CategoryType.CategoryDTO;
import com.atguigu.DTO.CategoryType.CategoryPageQueryDTO;
import com.atguigu.Entity.Category;
import com.atguigu.result.PageResult;
import com.atguigu.result.Result;
import com.atguigu.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminCategoryController")
@RequestMapping("/admin/category")
@Slf4j
@Tag(name = "分类管理-控制层")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类：POST
     * @param categoryDTO （json）
     * @return Result
     */
    @PostMapping
    @Operation(summary = "新增分类")
    public Result<String> save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    /**
     * 分类分页查询：GET
     * @param categoryPageQueryDTO （
     * @return Result<PageResult>
     */
    @GetMapping(value = "/page")
    @Operation(summary = "分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分页查询：{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 启用禁用分类：POST ( /status/{status} )
     * @param status （Integer）
     * @param id （Long）
     * @return Result<String>
     */
    @PostMapping("/status/{status}")
    @Operation(summary = "启用禁用分类")
    public Result<String> startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用分类：{}", status);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 分类删除：DELETE
     * @param id （Long）
     * @return Result<String>
     */
    @DeleteMapping
    @Operation(summary = "分类删除")
    public Result<String> delete(Long id){
        log.info("分类删除：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }

    /**
     * 修改分类：PUT
     * @param categoryDTO （json）
     * @return Result<String>
     */
    @PutMapping
    @Operation(summary = "修改分类")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO){
        log.info("修改分类：{}", categoryDTO);
        categoryService.updateInfo(categoryDTO);
        return Result.success();
    }

    /**
     * 根据类型查询分类列表
     * @param type 分类类型（1 菜品分类 2 套餐分类）
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
