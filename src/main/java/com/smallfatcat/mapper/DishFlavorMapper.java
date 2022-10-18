package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}
