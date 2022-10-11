package com.wjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.common.Message;
import com.wjy.pojo.ShoppingCart;

/**
 * @Author : 王金云
 * @create 2022/8/2 19:04
 */
public interface IShoppingCartService extends IService<ShoppingCart> {
    public void add(ShoppingCart shoppingCart);
}
