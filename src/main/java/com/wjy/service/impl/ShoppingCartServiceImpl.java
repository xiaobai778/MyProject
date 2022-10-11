package com.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import com.wjy.mapper.ShoppingCartMapper;
import com.wjy.pojo.ShoppingCart;
import com.wjy.service.IShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggerGroup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author : 王金云
 * @create 2022/8/2 19:05
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Transactional
    @Override
    public void add(ShoppingCart shoppingCart) {
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        if (dishId==null){
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }else {
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getDishId,dishId);
        }
        ShoppingCart shoppingCartOne = this.getOne(shoppingCartLambdaQueryWrapper);
        if (shoppingCartOne==null){
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            this.save(shoppingCart);
        }else{
            Integer number = shoppingCartOne.getNumber();
//            log.info("number++{}",number++);
            shoppingCartOne.setNumber(number+1);
//            shoppingCartOne.setCreateTime(LocalDateTime.now());
            this.updateById(shoppingCartOne);
        }
    }
}
