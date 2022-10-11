package com.wjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.dto.DishDto;
import com.wjy.pojo.Dish;

/**
 * @Author : 王金云
 * @create 2022/7/25 10:06
 */
public interface IDishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
    DishDto getByidWithFlavor(String id);
    void updateWithFlavor(DishDto dishDto);

}
