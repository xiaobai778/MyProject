package com.wjy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.mapper.EmployeeMapper;
import com.wjy.pojo.Employee;
import com.wjy.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/7/14 12:31
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {

}
