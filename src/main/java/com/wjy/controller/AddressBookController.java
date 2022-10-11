package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import com.wjy.pojo.AddressBook;
import com.wjy.pojo.User;
import com.wjy.service.IAddressBookService;
import com.wjy.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author : 王金云
 * @create 2022/8/2 9:32
 */
@Slf4j
@RequestMapping("/addressBook")
@RestController
public class AddressBookController {

    @Autowired
    private IAddressBookService iAddressBookService;
    @Autowired
    private IUserService iUserService;

    @PostMapping
    public Message<AddressBook> save(@RequestBody AddressBook addressBook){
        addressBook.setUserId(BaseContext.getCurrentId());
        iAddressBookService.save(addressBook);
        return Message.success(addressBook);
    }
    @GetMapping("/list")
    public Message<List<AddressBook>> list(){
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        addressBookLambdaQueryWrapper.orderByDesc(AddressBook::getUpdateTime);
        List<AddressBook> list = iAddressBookService.list(addressBookLambdaQueryWrapper);
        return Message.success(list);

    }
    @PutMapping("/default")
    public Message<String> setDefault(@RequestBody AddressBook addressBook){
        log.info("id:{}",addressBook);
        LambdaUpdateWrapper<AddressBook> addressBookLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        addressBookLambdaUpdateWrapper.eq(AddressBook::getId, BaseContext.getCurrentId());
        addressBookLambdaUpdateWrapper.set(AddressBook::getIsDefault,0);
        iAddressBookService.update(addressBookLambdaUpdateWrapper);
        addressBook.setIsDefault(1);
        iAddressBookService.updateById(addressBook);
        return Message.success("默认设置成功");
    }

    @GetMapping("/default")
    public Message<AddressBook> setDefault(){
        LambdaQueryWrapper<AddressBook> addressBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        addressBookLambdaQueryWrapper.eq(AddressBook::getUserId,BaseContext.getCurrentId());
        AddressBook addressBook = iAddressBookService.getOne(addressBookLambdaQueryWrapper);
        return Message.success(addressBook);
    }


}
