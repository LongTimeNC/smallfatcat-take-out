package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsz
 * @Description
 * @date 2022/10/16
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
