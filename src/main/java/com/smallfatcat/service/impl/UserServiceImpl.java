package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.entity.User;
import com.smallfatcat.mapper.UserMapper;
import com.smallfatcat.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
