package com.atguigu.mapper;

import com.atguigu.DTO.SetMealType.SetmealPageQueryDTO;
import com.atguigu.Entity.Setmeal;
import com.atguigu.VO.DishItemVO;
import com.atguigu.VO.SetmealVO;
import com.atguigu.annotation.AutoFill;
import com.atguigu.enumeration.OperationType;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 新增套餐
     * @param setmeal
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Setmeal setmeal);

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 套餐起售、停售
     * @param setmeal （停售、起售）
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateStatus(Setmeal setmeal);

    /**
     * 根据 ids 查询套餐信息
     * @param ids （List<Long>）
     * @return List<Setmeal>
     */
    List<Setmeal> listByIds(List<Long> ids);

    /**
     * 批量删除套餐
     * @param ids （List<Long>）
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据 id 查询套餐信息
     * @param id （Long）
     * @return Setmeal
     */
    @Select("select * from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 修改套餐信息
     * @param setmeal （Setmeal）
     */
    @AutoFill(value = OperationType.UPDATE)
    void updateInfo(Setmeal setmeal);

    /**
     * 根据分类 id 查询套餐
     * @param setmeal （categoryId, status）
     * @return List<Setmeal>
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐 id 查询菜品选项
     * @param setmealId （Long）
     * @return List<DishItemVO>
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemVOListBySetmealId(Long setmealId);
}
