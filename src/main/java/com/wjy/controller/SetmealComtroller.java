package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjy.common.Message;
import com.wjy.dto.SetmealDto;
import com.wjy.pojo.Category;
import com.wjy.pojo.Setmeal;
import com.wjy.pojo.SetmealDish;
import com.wjy.service.ICategoryService;
import com.wjy.service.ISetmealDishService;
import com.wjy.service.ISetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : 王金云
 * @create 2022/7/28 18:33
 */
@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealComtroller {
    @Autowired
    private ISetmealService iSetmealService;
    @Autowired
    private ISetmealDishService iSetmealDishService;
    @Autowired
    private ICategoryService iCategoryService;
    @GetMapping("page")
    public Message<Page> page(int page,int pageSize,String name){
        Page<Setmeal> setmealPage = new Page<>(page,pageSize);
        Page<SetmealDto> pageInfo = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(name!=null,Setmeal::getName,name);
        setmealLambdaQueryWrapper.orderByAsc(Setmeal::getUpdateTime);
        iSetmealService.page(setmealPage,setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(setmealPage,pageInfo,"records");
        List<Setmeal> records = setmealPage.getRecords();
        List<SetmealDto> list = records.stream().map((item->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item,setmealDto);
            Category category = iCategoryService.getById(item.getCategoryId());
            String categoryName = category.getName();
            setmealDto.setCategoryName(categoryName);
            return setmealDto;
        })).collect(Collectors.toList());
        pageInfo.setRecords(list);
        return Message.success(pageInfo);
    }
    @GetMapping("{id}")
    public Message<SetmealDto> get(@PathVariable String id){
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> list = iSetmealDishService.list(setmealDishLambdaQueryWrapper);
        SetmealDto setmealDto = new SetmealDto();
        Setmeal setmeal = iSetmealService.getById(id);
        BeanUtils.copyProperties(setmeal,setmealDto);
        setmealDto.setSetmealDishes(list);
//        setmealDto.getSetmealDishes().stream().map((item)->{
//
//        })
        return Message.success(setmealDto);

    }

    @PutMapping
    public Message<String> updateByIdWithDish(@RequestBody SetmealDto setmealDto){
        iSetmealService.updateByIdWithdish(setmealDto);



        return Message.success("更新成功");
    }
    @PostMapping
    public Message<String> save (@RequestBody SetmealDto setmealDto){
        log.info(setmealDto.toString());
        iSetmealService.saveWithDish(setmealDto);
        return Message.success("增加成功");
    }
    @DeleteMapping
    public Message<String> delete(@RequestParam List<String> ids){
        log.info(ids.toString());
        iSetmealService.deleteWithDish(ids);
        return Message.success("删除成功");
    }
}
