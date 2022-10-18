package com.smallfatcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallfatcat.dto.DishDto;
import com.smallfatcat.entity.Dish;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
public interface DishService extends IService<Dish> {

    //新增菜品，同时插入对应的口味数据
    //需要操作两张表，dish，dishflavor
    public void saveDishFlavor(DishDto dishDto);


    //根据id查询菜品和口味信息
    public DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    public void updateWithFlavor(DishDto dishDto);

}
