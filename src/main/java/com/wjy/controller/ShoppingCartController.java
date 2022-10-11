package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import com.wjy.pojo.Setmeal;
import com.wjy.pojo.ShoppingCart;
import com.wjy.service.IShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : 王金云
 * @create 2022/8/2 19:03
 */
@Slf4j
@RestController

@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private IShoppingCartService iShoppingCartService;



    @PostMapping("/add")
    public Message<String> add(@RequestBody ShoppingCart shoppingCart){
        iShoppingCartService.add(shoppingCart);
        return Message.success("chenggong");
    }
    @GetMapping("/list")
    public Message<List<ShoppingCart>> list(){
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        shoppingCartLambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = iShoppingCartService.list(shoppingCartLambdaQueryWrapper);
        return Message.success(list);
    }
    @DeleteMapping("/clean")
    public Message<String> clean(){
        LambdaUpdateWrapper<ShoppingCart> shoppingCartLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        shoppingCartLambdaUpdateWrapper.eq(ShoppingCart::getUserId ,BaseContext.getCurrentId());
        iShoppingCartService.remove(shoppingCartLambdaUpdateWrapper);
        return Message.success("清空购物车成功");
    }

//    @PostMapping("/sub")
//    public Message<String> sub( @RequestParam("dishId"),@RequestParam("SetmealId"){
//        LambdaUpdateWrapper<ShoppingCart> shoppingCartLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
//        shoppingCartLambdaUpdateWrapper.eq(ShoppingCart::getUserId ,BaseContext.getCurrentId());
//        shoppingCartLambdaUpdateWrapper.eq(ShoppingCart::getDishId,dishId);
//        ShoppingCart shoppingCartServiceOne = iShoppingCartService.getOne(shoppingCartLambdaUpdateWrapper);
//        Integer number = shoppingCartServiceOne.getNumber();
//        shoppingCartServiceOne.setNumber(number-1);
//        iShoppingCartService.updateById(shoppingCartServiceOne);
//        return Message.success("减少成功");
//    }


}
