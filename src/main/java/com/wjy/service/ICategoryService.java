package com.wjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.pojo.Category;

/**
 * @Author : 王金云
 * @create 2022/7/24 18:42
 */
public interface ICategoryService extends IService<Category> {
    void remove(Long id);
}
