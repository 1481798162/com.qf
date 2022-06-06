package com.qf.ruigie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qf.ruigie.mapper.EmployeeMapper;
import com.qf.ruigie.pojo.Employee;
import com.qf.ruigie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
