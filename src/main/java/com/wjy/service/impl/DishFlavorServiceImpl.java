package com.wjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.mapper.DishFlavorMapper;
import com.wjy.mapper.DishMapper;
import com.wjy.pojo.DishFlavor;
import com.wjy.service.IDishFlavorService;
import com.wjy.service.IEmployeeService;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/7/25 18:46
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {
}
