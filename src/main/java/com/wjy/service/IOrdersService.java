package com.wjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.pojo.Orders;

/**
 * @Author : ηιδΊ
 * @create 2022/8/5 14:46
 */
public interface IOrdersService extends IService<Orders> {
    void submit(Orders orders);
}
