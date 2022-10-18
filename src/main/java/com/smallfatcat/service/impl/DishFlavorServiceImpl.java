package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.entity.DishFlavor;
import com.smallfatcat.mapper.DishFlavorMapper;
import com.smallfatcat.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description
 * @date 2022/10/6
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
