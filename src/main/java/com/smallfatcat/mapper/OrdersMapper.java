package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsz
 * @Description
 * @date 2022/10/12
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
