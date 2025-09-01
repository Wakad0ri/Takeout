package com.atguigu.mapper;

import com.atguigu.DTO.DishType.DishPageQueryDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.VO.DishVO;
import com.atguigu.annotation.AutoFill;
import com.atguigu.enumeration.OperationType;
import com.github.pagehelper.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
@Tag(name = "菜品管理-数据访问层")
public interface DishMapper {

    /**
     * 新增菜品
     * @param dish （
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 分页查询
     *
     * @param dishPageQueryDTO （
     * @return Page<DishVO>
     */
    Page<DishVO> pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 启用禁用菜品
     * @param dish （
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateStatus(Dish dish);

    /**
     * 根据id查询菜品状态
     *
     * @param id （
     * @return dish
     */
    @Select("select * from dish where id = #{id}")
    Dish getDishById(Long id);

//    /**
//     * 根据id删除菜品
//     *
//     * @param id
//     */
//    @Delete("delete from dish where id = #{id}")
//    void deleteById(Long id);

    /**
     * 根据id批量删除菜品
     * @param ids （
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询菜品信息
     * @param id （Long）
     * @return dish
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);

    /**
     * 修改菜品信息
     * @param dish （
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateInfo(Dish dish);

    /**
     * 根据 Dish的信息(name, status, 分类id) 查询菜品
     * @param dish （
     * @return List<Dish>
     * 在 新增套餐 时需要
     */
    List<Dish> list(Dish dish);

    /**
     * 根据套餐id查询菜品
     * @param setmealId 套餐ID
     * @return List<Dish>
     */
    @Select("select d.* from dish d inner join setmeal_dish sd on d.id = sd.dish_id where sd.setmeal_id = #{setmealId}")
    List<Dish> getBySetmealId(Long setmealId);
}
