package com.wjy.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.mapper.OrderDetailMapper;
import com.wjy.pojo.OrderDetail;
import com.wjy.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/8/5 14:54
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {
}
