package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.entity.ShoppingCart;
import com.smallfatcat.mapper.ShoppingCartMapper;
import com.smallfatcat.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Service
public class ShoppingServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
