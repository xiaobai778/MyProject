package com.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.common.BaseContext;
import com.wjy.common.CustomException;
import com.wjy.mapper.OrdersMapper;
import com.wjy.pojo.*;
import com.wjy.service.*;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Author : 王金云
 * @create 2022/8/5 14:52
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IAddressBookService iAddressBookService;
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderDetailService iOrderDetailService;


    @Override
    @Transactional
    public void submit(Orders orders) {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = iShoppingCartService.list(shoppingCartLambdaQueryWrapper);
        if (shoppingCarts==null || shoppingCarts.size()==0){
            new CustomException("购物车为空");
        }
        User user = iUserService.getById(userId);
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = iAddressBookService.getById(addressBookId);
        if (addressBook==null){
            new CustomException("请填写地址");
        }

        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger();

        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setNumber(item.getNumber());
            orderDetail.setAmount(item.getAmount());
            amount.getAndAdd(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());



        orders.setNumber(String.valueOf(orderId));
        orders.setUserId(userId);
        orders.setAddressBookId(addressBookId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setPhone(addressBook.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        this.save(orders);
        iOrderDetailService.saveBatch(orderDetails);



        iShoppingCartService.remove(shoppingCartLambdaQueryWrapper);

    }
}
