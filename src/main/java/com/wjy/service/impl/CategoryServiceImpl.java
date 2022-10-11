package com.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.common.CustomException;
import com.wjy.mapper.CategoryMapper;
import com.wjy.pojo.Category;
import com.wjy.pojo.Dish;
import com.wjy.pojo.Setmeal;
import com.wjy.service.ICategoryService;
import com.wjy.service.IDishService;
import com.wjy.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : 王金云
 * @create 2022/7/24 18:44
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {
    @Autowired
    private IDishService iDishService;
    @Autowired
    private ISetmealService iSetmealService;




    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = iDishService.count(dishLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("删除菜品异常，");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = iSetmealService.count(setmealLambdaQueryWrapper);
        if (count1>0){
            throw new CustomException("删除套餐异常，");
        }

        super.removeById(id);
    }
}
