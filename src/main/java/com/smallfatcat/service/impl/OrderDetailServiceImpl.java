package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.entity.OrderDetail;
import com.smallfatcat.mapper.OrderDetailMapper;
import com.smallfatcat.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
