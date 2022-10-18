package com.smallfatcat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smallfatcat.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zsz
 * @Description-员工dao层
 * @date 2022/10/5
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
