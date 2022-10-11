package com.wjy.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.mapper.AddressBookMapper;
import com.wjy.pojo.AddressBook;
import com.wjy.service.IAddressBookService;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/8/2 9:29
 */
@Service
public class AddressBookImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {
}
