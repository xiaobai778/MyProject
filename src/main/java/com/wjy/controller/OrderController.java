package com.wjy.controller;

import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import com.wjy.pojo.Orders;
import com.wjy.service.IOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author : 王金云
 * @create 2022/8/5 15:03
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private IOrdersService iOrdersService;

    /**\
     * 用户提交订单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public Message<String> submit(@RequestBody Orders orders){
        iOrdersService.submit(orders);
        return Message.success("提交成功");
    }

}
