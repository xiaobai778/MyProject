package com.wjy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wjy.dto.SetmealDto;
import com.wjy.pojo.Setmeal;

import java.util.List;

/**
 * @Author : ηιδΊ
 * @create 2022/7/25 10:05
 */
public interface ISetmealService extends IService<Setmeal> {
    void updateByIdWithdish(SetmealDto setmealDto);
    void saveWithDish(SetmealDto setmealDto);
    void deleteWithDish(List<String> ids);
}
