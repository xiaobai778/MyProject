package com.wjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjy.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : 王金云
 * @create 2022/7/31 20:43
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
