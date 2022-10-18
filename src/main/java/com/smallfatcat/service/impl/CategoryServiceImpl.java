package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.common.MyException;
import com.smallfatcat.entity.Category;
import com.smallfatcat.entity.Dish;
import com.smallfatcat.entity.Setmeal;
import com.smallfatcat.mapper.CategoryMapper;
import com.smallfatcat.service.CategoryService;
import com.smallfatcat.service.DishService;
import com.smallfatcat.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description-分类管理业务层
 * @date 2022/10/6
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * 根据id删除分类，删除之前进行判断
     *
     * @param id
     */
    @Override
    public void remove(Long id) {
        //查询当前分类是否关联了菜品,如果关联了直接抛出一个业务异常
        LambdaQueryWrapper<Dish> queryWrapper1 = new LambdaQueryWrapper<>();
        //添加条件，根据分类id进行查询
        queryWrapper1.eq(Dish::getCategoryId, id);
        int count1 = dishService.count(queryWrapper1);
        if (count1 > 0) {
            throw new MyException("当前分类下关联了菜品，不能删除");
        }
        //查询当前分类是否关联了套餐,如果关联了直接抛出一个业务异常
        LambdaQueryWrapper<Setmeal> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Setmeal::getCategoryId, id);
        int count2 = setmealService.count(queryWrapper2);
        if (count2 > 0) {
            throw new MyException("当前分类下关联了套餐，不能删除");
        }
        //都没有的话，调用父类方法正常删除
        super.removeById(id);
    }
}
