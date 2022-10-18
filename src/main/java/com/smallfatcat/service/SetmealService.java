package com.smallfatcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallfatcat.dto.SetmealDto;
import com.smallfatcat.entity.Dish;
import com.smallfatcat.entity.Setmeal;

import java.util.List;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
public interface SetmealService extends IService<Setmeal> {

    //新增套餐，同时保存套餐和菜品的关联关系
    public void saveWhitDish(SetmealDto setmealDto);

    //根据id查询对应的套餐信息
    public SetmealDto getByIdWithDish(Long id);

    //修改对应的套餐和套餐里面的菜品
    public void updateWithDish(SetmealDto setmealDto);

    //删除套餐的方法
    public void removeWithDish(List<Long> ids);

    //停售套餐的方法
    public void updateStatus(List<Long> ids);

    //启用套餐的方法
    public void updateStatusUse(List<Long> ids);
}
