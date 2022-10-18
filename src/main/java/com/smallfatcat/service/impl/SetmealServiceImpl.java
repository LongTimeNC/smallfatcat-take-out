package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.common.MyException;
import com.smallfatcat.dto.SetmealDto;
import com.smallfatcat.entity.Setmeal;
import com.smallfatcat.entity.SetmealDish;
import com.smallfatcat.mapper.SetmealMapper;
import com.smallfatcat.service.SetmealDishService;
import com.smallfatcat.service.SetmealService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * 新增套餐，同时保存套餐和菜品的关联关系
     *
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWhitDish(SetmealDto setmealDto) {
        //保存套餐的基本信息-操作setmeal
        this.save(setmealDto);
        //保存套餐和菜品的关联信息-操作的是setmeal_dish
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 根据id查询套餐和套餐包含的菜品
     *
     * @param id
     * @return
     */
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        //先查询菜品基本信息
        Setmeal setmeal = this.getById(id);
        //使用对象拷贝
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        //在查询菜品口味信息
        //查询条件构造器
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(SetmealDish::getSetmealId, setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(queryWrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    /**
     * 更新套餐信息，同时更新对应的菜品信息
     *
     * @param setmealDto
     */
    @Transactional
    @Override
    public void updateWithDish(SetmealDto setmealDto) {
        //更新dish表基本信息
        this.updateById(setmealDto);
        //清理当前菜品对应的口味信息-dish_flavor表的delete操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SetmealDish::getSetmealId, setmealDto.getId());
        setmealDishService.remove(queryWrapper);
        //添加当前提交过来的菜品信息
        List<SetmealDish> flavors = setmealDto.getSetmealDishes();
        flavors = flavors.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(flavors);
    }

    /**
     * 删除套餐和对应的菜品信息
     *
     * @param ids
     */
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        //先查询套餐状态，确定是否可以删除
        //条件构造器
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(queryWrapper);
        //如果不能删除，抛出一个异常，不能删除
        if (count > 0) {
            throw new MyException("正在售卖中不能删除!");
        }
        //如果可以删除，先删除套餐表里面的数据,删除setmeal表中的套餐信息
        this.removeByIds(ids);
        //再删除关系表中的数据，删除setmeal_dish关联表中的菜品信息
        LambdaQueryWrapper<SetmealDish> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(queryWrapper1);
    }

    /**
     * 停售套餐
     *
     * @param ids
     */
    @Override
    public void updateStatus(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = this.list(queryWrapper);
        for (Setmeal setmeal : list) {
            setmeal.setStatus(0);
            this.updateById(setmeal);
        }
    }

    /**
     * 启用套餐
     *
     * @param ids
     */
    @Override
    public void updateStatusUse(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = this.list(queryWrapper);
        for (Setmeal setmeal : list) {
            setmeal.setStatus(1);
            this.updateById(setmeal);
        }
    }


}
