package com.wjy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wjy.dto.DishDto;
import com.wjy.mapper.DishMapper;
import com.wjy.pojo.Dish;
import com.wjy.pojo.DishFlavor;
import com.wjy.service.IDishFlavorService;
import com.wjy.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : 王金云
 * @create 2022/7/25 10:06
 */
@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService {
    @Autowired
    private IDishFlavorService iDishFlavorService;

    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        this.save(dishDto);

        Long dishId = dishDto.getId();

        List<DishFlavor> flavors = dishDto.getFlavors();

        for (DishFlavor dishFlavor:flavors) {
            dishFlavor.setDishId(dishId);
        }
        log.info(flavors.toString());
        iDishFlavorService.saveBatch(flavors);

    }

    @Override
    public DishDto getByidWithFlavor(String id) {
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        Long flavorId = dish.getId();
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,flavorId);
        List<DishFlavor> list = iDishFlavorService.list(dishFlavorLambdaQueryWrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }


    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        iDishFlavorService.remove(dishFlavorLambdaQueryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        iDishFlavorService.saveBatch(flavors);


    }
}
