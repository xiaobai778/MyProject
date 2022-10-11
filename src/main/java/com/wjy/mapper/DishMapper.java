package com.wjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjy.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : 王金云
 * @create 2022/7/25 10:03
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
