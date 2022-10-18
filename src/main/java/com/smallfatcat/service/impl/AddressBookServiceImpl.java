package com.smallfatcat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smallfatcat.entity.AddressBook;
import com.smallfatcat.mapper.AddressBookMapper;
import com.smallfatcat.service.AddressBookService;
import org.springframework.stereotype.Service;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
