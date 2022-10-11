package com.wjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.mapper.UserMapper;
import com.wjy.pojo.User;
import com.wjy.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/7/31 20:41
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
