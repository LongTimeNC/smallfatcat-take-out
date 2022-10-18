package com.smallfatcat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smallfatcat.entity.Orders;

/**
 * @author zsz
 * @Description
 * @date 2022/10/12
 */
public interface OrdersService extends IService<Orders> {

    //用户下单
    public void submit(Orders orders);
}
