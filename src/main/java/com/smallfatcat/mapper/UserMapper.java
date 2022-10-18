package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
