package com.qf.ruigie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qf.ruigie.common.R;
import com.qf.ruigie.pojo.Employee;
import com.qf.ruigie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request) {
        log.info("{}", employee);
        //1.将页面提交的密码进行MD5加密处理
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名username查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //3.如果没有查询到则返回登录失败结果
        if (emp == null) {
            return R.error("登录失败");
        }
        //4.密码比对，如果不一致则返回登录失败结果
        if (!emp.getPassword().equals(password)) {
            return R.error("登录失败");
        }
        //5.查看员工状态，如果为已禁用状态，则返回员工已禁用结果
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }
        //6.登录成功，将员工id存入session并返回登录成功结果
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        //清理Session中保存的当前员工登入的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工,员工信息:{}", employee.toString());

        //设置初始密码,密码使用MD5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        //设置时间,获取当前时间
//        employee.setCreateTime(LocalDateTime.now());
//        //设置更新时间
//        employee.setUpdateTime(LocalDateTime.now());
//
//        //获取当前登入用户的id
//        Long empid = (Long) request.getSession().getAttribute("employee");
//
//        employee.setCreateUser(empid);
//        employee.setUpdateUser(empid);

        employeeService.save(employee);

        return R.success("新增员工成功");

    }

    /**
     * 员工信息分页查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pasgeSize ={},name ={}", page, pageSize, name);

        //构造分页构造器
        Page pageinfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageinfo, queryWrapper);
        return R.success(pageinfo);
    }

    /**
     * 根据员工id来修改信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为: {}",id);

//        Long empId = (Long) request.getSession().getAttribute("employee");
//        //设置当前修改的时间
//        employee.setUpdateTime(LocalDateTime.now());
//        //设置当前那个用户修改的
//        employee.setUpdateUser(empId);

        employeeService.updateById(employee);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id来查询员工信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable long id) {
        log.info("根据id查询员工信息");
        Employee employee = employeeService.getById(id);
        if(employee !=null){
            return R.success(employee);
        }
        return R.error("没有查询到该员工");
    }

}
