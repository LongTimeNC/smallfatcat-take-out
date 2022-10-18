package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
