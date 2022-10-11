package com.wjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjy.pojo.Category;
import com.wjy.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : 王金云
 * @create 2022/7/24 18:41
 */
@Mapper
public interface CategoryMapper  extends BaseMapper<Category> {
}
