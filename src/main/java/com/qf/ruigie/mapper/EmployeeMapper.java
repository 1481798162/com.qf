package com.qf.ruigie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.ruigie.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
