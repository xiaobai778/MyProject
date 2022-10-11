package com.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.common.CustomException;
import com.wjy.dto.SetmealDto;
import com.wjy.mapper.SetmealMapper;
import com.wjy.pojo.Setmeal;
import com.wjy.pojo.SetmealDish;
import com.wjy.service.ISetmealDishService;
import com.wjy.service.ISetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : 王金云
 * @create 2022/7/25 10:08
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements ISetmealService {


    @Autowired
    private ISetmealDishService iSetmealDishService;
    @Override
    @Transactional
    public void updateByIdWithdish(SetmealDto setmealDto) {
        this.updateById(setmealDto);
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            boolean equals = iSetmealDishService.equals(setmealDish);
            if (equals){
                iSetmealDishService.updateById(setmealDish);
            }else {
                setmealDish.setSetmealId(setmealDto.getId());
                iSetmealDishService.save(setmealDish);
            }
        }
    }

    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        for (SetmealDish setmealDish : setmealDto.getSetmealDishes()) {
            setmealDish.setSetmealId(setmealDto.getCategoryId());
        }

        iSetmealDishService.saveBatch(setmealDto.getSetmealDishes());


    }

    @Override
    public void deleteWithDish(List<String> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getId,ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(setmealLambdaQueryWrapper);
        if (count>0){
            throw new CustomException("售卖中，不能删除");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);
        iSetmealDishService.remove(setmealDishLambdaQueryWrapper);

    }
}
