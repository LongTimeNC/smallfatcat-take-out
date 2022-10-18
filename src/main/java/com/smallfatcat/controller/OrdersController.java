package com.smallfatcat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallfatcat.common.Result;
import com.smallfatcat.entity.Orders;
import com.smallfatcat.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zsz
 * @Description
 * @date 2022/10/12
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/page")
    public Result<Page> page(int page, int pageSize, String number) {
        log.info("page: {},pageSize: {},number: {}", page, pageSize, number);
        //分页构造器
        Page<Orders> pageInfo = new Page<>(page, pageSize);
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加一个过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(number),Orders::getNumber, number);
        //添加一个排序条件
        queryWrapper.orderByDesc(Orders::getCheckoutTime);
        ordersService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 下单支付
     *
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Result<String> submit(@RequestBody Orders orders) {
        log.info("订单信息 : {}",orders);
        ordersService.submit(orders);
        return Result.success("下单成功！");
    }


}
