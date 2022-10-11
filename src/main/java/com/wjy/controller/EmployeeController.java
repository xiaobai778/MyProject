package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjy.common.Message;
import com.wjy.pojo.Employee;
import com.wjy.service.IEmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * @Author : 王金云
 * @create 2022/7/14 12:35
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private IEmployeeService iEmployeeService;

    @PostMapping("/login")
    public Message<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        String password = employee.getPassword();

        password= DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));


        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = iEmployeeService.getOne(queryWrapper);


        if (emp==null){
            return Message.error("没有此用户");
        }

        if (!emp.getPassword().equals(password)){
            return Message.error("密码错误");
        }


        if (emp.getStatus()==0){
            return Message.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",emp.getId());
        return Message.success(emp);
    }
    @PostMapping("/logout")
    public Message<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return Message.success("退出成功");
    }
    @PostMapping
    public Message<String> save(@RequestBody Employee employee,HttpServletRequest request){
        log.info("添加员工信息为：{}",employee.toString());

        String empPassWord = DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8));
        employee.setPassword(empPassWord);

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//
//        Long empId =(Long) request.getSession().getAttribute("employee");
//        employee.setCreateUser(empId);
//        employee.setUpdateUser(empId);


        iEmployeeService.save(employee);
        return Message.success("增加成功");
    }

    @GetMapping("/page")
    public Message<Page> page(int page, int pageSize,String name){
        Page pageInfo = new Page(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        iEmployeeService.page(pageInfo,lambdaQueryWrapper);
        return Message.success(pageInfo);
    }
    @PutMapping
    public Message<String> update(HttpServletRequest request,@RequestBody Employee employee){
        long id = Thread.currentThread().getId();
        log.info("线程ID为{}",id);
        Long empId =(Long) request.getSession().getAttribute("employee");
        log.info(empId.toString());
        employee.setUpdateUser(empId);
//        employee.setUpdateTime(LocalDateTime.now());

        iEmployeeService.updateById(employee);

        return Message.success("修改成功");
    }
    @GetMapping("/{id}")
    public Message<Employee> getId(@PathVariable Long id){
        Employee employee = iEmployeeService.getById(id);
        if (employee!=null){
            return Message.success(employee);
        }
        return Message.error("未查询到员工");

    }
}
