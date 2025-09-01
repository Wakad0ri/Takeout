package com.atguigu.service.impl;

import com.atguigu.DTO.SetMealType.SetmealDTO;
import com.atguigu.DTO.SetMealType.SetmealPageQueryDTO;
import com.atguigu.Entity.Dish;
import com.atguigu.Entity.Setmeal;
import com.atguigu.Entity.SetmealDish;
import com.atguigu.VO.DishItemVO;
import com.atguigu.VO.SetmealVO;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.StatusConstant;
import com.atguigu.exception.BaseException;
import com.atguigu.exception.SetmealEnableFailedException;
import com.atguigu.mapper.DishMapper;
import com.atguigu.mapper.SetmealDishMapper;
import com.atguigu.mapper.SetmealMapper;
import com.atguigu.result.PageResult;
import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Tag(name = "套餐管理-服务层")
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     * @param setmealDTO （json）
     */
    @Override
    @Transactional
    public void save(SetmealDTO setmealDTO){
        // 业务规则验证
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            throw new BaseException("套餐必须包含菜品");
        }
        // 保存套餐基本信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmeal.setStatus(StatusConstant.DISABLE); // 新增套餐默认停售
        setmealMapper.insert(setmeal);
        // 获取生成的套餐ID
        Long setmealId = setmeal.getId();
        // 保存套餐菜品关联关系
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealId));
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 套餐分页查询
     * @param setmealPageQueryDTO （j
     * @return PageResult
     */
    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.pageQuery(setmealPageQueryDTO);
        long count = page.getTotal();
        List<SetmealVO> records = page.getResult();
        return new PageResult(count, records);
    }

    /**
     * 启用禁用套餐
     * @param status 状态（0停售，1起售）
     * @param id 套餐ID
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        if(StatusConstant.ENABLE.equals(status)){
            List<Dish> dishes = dishMapper.getBySetmealId(id);
            if(dishes != null && !dishes.isEmpty()){
                dishes.forEach(dish -> {
                    if(StatusConstant.DISABLE.equals(dish.getStatus())){
                        throw new SetmealEnableFailedException(MessageConstant.SETMEAL_ENABLE_FAILED);
                    }
                });
            }
        }
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.updateStatus(setmeal);
    }

    /**
     * 批量删除套餐
     * @param ids 套餐ID列表
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        List<Setmeal> setmeals = setmealMapper.listByIds(ids);
        setmeals.forEach(setmeal -> {
            if(StatusConstant.ENABLE.equals(setmeal.getStatus())){
                throw new BaseException(MessageConstant.SETMEAL_ON_SALE);
            }
        });

        setmealDishMapper.deleteBatchBySetmealIds(ids);

        setmealMapper.deleteBatch(ids);
    }

    /**
     * 根据id查询套餐信息
     * @param id 套餐ID
     * @return SetmealVO
     */
    @Override
    public SetmealVO getByIdWithDishes(Long id) {
        // 1. 查询套餐基本信息
        Setmeal setmeal = setmealMapper.getById(id);
        if (setmeal == null) {
            throw new BaseException("套餐不存在");
        }

        // 2. 查询套餐菜品关联信息
        List<SetmealDish> setmealDishes = setmealDishMapper.getBySetmealId(id);
        if (setmealDishes == null) {
            setmealDishes = new ArrayList<>();
        }

        // 3. 转换为VO对象
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO 套餐数据传输对象
     */
    @Override
    @Transactional
    public void updateInfo(SetmealDTO setmealDTO) {
        // 1. 参数验证
        if (setmealDTO == null || setmealDTO.getId() == null) {
            throw new BaseException("套餐信息不能为空");
        }

        // 2. 业务规则验证
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if (setmealDishes == null || setmealDishes.isEmpty()) {
            throw new BaseException("套餐必须包含菜品");
        }

        // 3. 更新套餐基本信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateInfo(setmeal);

        // 4. 删除原有的套餐菜品关联关系
        List<Long> setmealIds = new ArrayList<>();
        setmealIds.add(setmealDTO.getId());
        setmealDishMapper.deleteBatchBySetmealIds(setmealIds);

        // 5. 保存新的套餐菜品关联关系
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealDTO.getId()));
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * 根据分类id查询套餐
     * @param setmeal 套餐
     * @return List<Setmeal>
     * 浏览套餐时需要
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        return setmealMapper.list(setmeal);
    }

    /**
     * 根据套餐id查询套餐详情
     * @param setmealId 套餐ID
     * @return List<DishItemVO>
     * 浏览套餐时需要
     */
    @Override
    public List<DishItemVO> getDishItemVOListBySetmealId(Long setmealId) {
        return setmealMapper.getDishItemVOListBySetmealId(setmealId);
    }

}
