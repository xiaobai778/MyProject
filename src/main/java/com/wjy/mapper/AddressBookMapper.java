package com.wjy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wjy.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author : 王金云
 * @create 2022/8/2 9:30
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
