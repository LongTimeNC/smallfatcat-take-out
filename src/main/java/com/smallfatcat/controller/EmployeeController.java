package com.smallfatcat.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smallfatcat.common.Result;
import com.smallfatcat.entity.Employee;
import com.smallfatcat.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zsz
 * @Description-员工接口
 * @date 2022/10/5
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登陆接口
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public Result<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        //1：将页面提交的密码进行md5加密处理
        String password = employee.getPassword();
        //md5加密工具类
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        //2：根据页面提交的用户名username查询数据库
        //2.1：先封装条件
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        //2.2：用户名是唯一的，所以调用getOne方法查询
        Employee emp = employeeService.getOne(queryWrapper);
        //3：如果没有查询到则返回登录失败
        if (emp == null) {
            return Result.error("登录失败!");
        }
        //4：密码比对，如果不一致则返回登录失败
        if (!emp.getPassword().equals(password)) {
            return Result.error("登录失败!");
        }
        //5：查看员工状态，如果已禁用，返回员工已禁用
        if (emp.getStatus() == 0) {
            return Result.error("账号已禁用!");
        }
        //6：登录成功，将员工id存入Session并返回登录结构成功
        request.getSession().setAttribute("employee", emp.getId());
        return Result.success(emp);
    }

    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        //1：清除Session中的用户id
        request.getSession().removeAttribute("employee");
        return Result.success("退出成功!");
    }

    /**
     * 新增员工
     *
     * @param employee
     * @return
     */
    @PostMapping
    public Result<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息: {}", employee.toString());
        //设置初始密码，使用md5进行加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        //设置创建时间
//        employee.setCreateTime(LocalDateTime.now());
//        //设置更新时间
//        employee.setUpdateTime(LocalDateTime.now());
//        //设置创建员工的的创建人
//        //获取当前登录用户的id
//        long empId = (long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);
        boolean save = employeeService.save(employee);
        if (save) {
            return Result.success("新增员工成功!");
        } else {
            return Result.error("新增员工失败!");
        }
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
    public Result<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        //构造分页构造器
        Page pageInfo = new Page(page, pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加一个过滤条件
        queryWrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        //添加一个排序条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);
        //查询
        employeeService.page(pageInfo, queryWrapper);
        return Result.success(pageInfo);
    }

    /**
     * 根据id查询员工信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return Result.success(employee);
        } else {
            return Result.error("更新员工失败!");
        }
    }

    /**
     * 根据id修改员工信息
     *
     * @param employee
     * @return
     */
    @PutMapping
    public Result<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        long id = Thread.currentThread().getId();
        log.info("线程ID：{}",id);
//        employee.setUpdateTime(LocalDateTime.now());
//        //获取当前登录用户的id
//        long empId = (long) request.getSession().getAttribute("employee");
//        employee.setUpdateUser(empId);
        boolean b = employeeService.updateById(employee);
        if (b) {
            return Result.success("更新员工成功!");
        } else {
            return Result.error("更新员工失败!");
        }
    }


}
