package com.wjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjy.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : 王金云
 * @create 2022/7/14 12:23
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
