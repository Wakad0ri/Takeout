package com.atguigu.service.impl;

import com.atguigu.DTO.DishType.DishDTO;
import com.atguigu.DTO.DishType.DishPageQueryDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.Entity.DishFlavor;
import com.atguigu.VO.DishVO;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.StatusConstant;
import com.atguigu.exception.DeletionNotAllowedException;
import com.atguigu.mapper.DishFlavorMapper;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.SetmealDishMapper;
import com.atguigu.result.PageResult;
import com.atguigu.service.DishService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Tag(name = "菜品管理-服务层")
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * 新增菜品，同时保存口味数据
     * @param dishDTO （流程：生成dish对象并copyDTO -> 向菜品表插入数据&获取插入菜品的ID ->
     *                      生成List<DishFlavor>接受DTO的口味数据 -> 判断flavors!=null&!isEmpty{
     *                      flavors.forEach给dishFlavors设置得到的dishId} -> 向口味表插入n条数据）
     */
    @Override
    public void saveWithFlavor(DishDTO dishDTO){
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        // xml中Insert配置中
        // useGeneratedKeys="true" 表示使用数据库自动生成的主键
        // keyProperty="id" 表示将生成的主键赋值给id属性
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && !flavors.isEmpty()){
            flavors.forEach(dishFlavor ->
                    dishFlavor.setDishId(dishId)
            );
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO （
     * @return PageResult
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> records = page.getResult();

        return new PageResult(total, records);
    }

    /**
     * 启用禁用菜品
     * @param status（Integer）
     * @param id（Long）
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();
        dishMapper.updateStatus(dish);
    }

    /**
     * 删除菜品
     * @param ids（List<Long>）
     *
     * 涉及到多个表查询，所以需要加上@Transactional注解
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        // 判断当前菜品是否是在起售中
        for( Long id : ids ){
            Dish dish = dishMapper.getDishById(id);
            if(Objects.equals(dish.getStatus(), StatusConstant.ENABLE)){
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        // 判断当前菜品是否关联了套餐
        List<Long> SetmealIds = setmealDishMapper.getSetMealDishIds(ids);
        if ( SetmealIds != null && !SetmealIds.isEmpty()){
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        // 删除菜品表中的菜品数据
//        for (Long id : ids){
//            dishMapper.deleteById(id);
//            // 删除菜品关联的口味数据
//            dishFlavorMapper.deleteByDishId(id);
//        } 可以进行优化，用一条sql解决
        dishMapper.deleteBatch(ids);
        dishFlavorMapper.deleteBatch(ids);
    }

    /**
     * 根据id查询菜品信息
     * @param id （Long）
     * @return DishVO
     */
    @Override
    public DishVO getByIdWithFlavors(Long id) {
        Dish dish = dishMapper.getById(id);
        List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);
        return dishVO;
    }

    /**
     * 修改菜品信息
     * @param dishDTO （DishDTO）
     */
    @Override
    public void updateInfo(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);
        dishMapper.updateInfo(dish);

        // 还需要删除原有的口味数据，同时插入新的口味数据
        Long dishId = dishDTO.getId();
        List<DishFlavor> flavors = dishDTO.getFlavors();
        dishFlavorMapper.deleteByDishId(dishId);
        if (flavors != null && !flavors.isEmpty()) {
            dishDTO.getFlavors().forEach(dishFlavor -> dishFlavor.setDishId(dishId));
            dishFlavorMapper.insertBatch(flavors);
        }
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类ID
     * @param name 菜品名称（可选，支持模糊查询）
     * @return List<Dish>
     * 在新增套餐时需要
     */
    @Override
    public List<Dish> list(Long categoryId, String name) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .name(name)
                .status(StatusConstant.ENABLE) // 只查询启用的菜品
                .build();
        return dishMapper.list(dish);
    }

    /**
     * 根据套餐id查询菜品和菜品对应的口味数据
     * @param dish （id）
     * @return List<Dish>
     * 在浏览菜品时也需要
     */
    @Override
    public List<DishVO> getDishVOListByDish(Dish dish) {
        List<Dish> dishes = dishMapper.list(dish);

        List<DishVO> dishVOList = new ArrayList<>();
        for (Dish d : dishes) {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(d, dishVO);
            List<DishFlavor> flavors = dishFlavorMapper.getFlavorsByDishId(d.getId());
            dishVO.setFlavors(flavors);
            dishVOList.add(dishVO);
        }
        return dishVOList;
    }
}
