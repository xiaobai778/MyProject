package com.wjy.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjy.common.Message;
import com.wjy.dto.DishDto;
import com.wjy.pojo.Category;
import com.wjy.pojo.Dish;
import com.wjy.pojo.DishFlavor;
import com.wjy.service.ICategoryService;
import com.wjy.service.IDishFlavorService;
import com.wjy.service.IDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author : 王金云
 * @create 2022/7/25 12:29
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private IDishService iDishService;
    @Autowired
    private IDishFlavorService iDishFlavorService;
    @Autowired
    private ICategoryService iCategoryService;
//    注入redis
    @Autowired
    private RedisTemplate redisTemplate;



    @PostMapping
    public Message<String> save(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        iDishService.saveWithFlavor(dishDto);
        return Message.success("新增成功");
    }





    @GetMapping("/page")
    public Message<Page> page(int page, int pageSize){
        log.info("page========{}",page);
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.orderByAsc(Dish::getUpdateTime);
        iDishService.page(pageInfo,dishLambdaQueryWrapper);
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();

            Category category = iCategoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDto.setCategoryName(categoryName);
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);

        return Message.success(dishDtoPage);
    }
    @GetMapping("/{id}")
    public Message<DishDto> get(@PathVariable  String id){
        DishDto withFlavor = iDishService.getByidWithFlavor(id);
        return Message.success(withFlavor);
    }

    /**
     *
     * @param categoryId
     * @return
     */
//    @GetMapping("/list")
//    public Message<List<Dish>> list(Long categoryId ){
//        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
//        dishLambdaQueryWrapper.eq(categoryId!=null,Dish::getCategoryId,categoryId);
//        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = iDishService.list(dishLambdaQueryWrapper);
//        return Message.success(list);
//    }

    @GetMapping("/list")
    public Message<List<DishDto>> list(Long categoryId ){
//        声明list变量
        List<DishDto> dishDtoList;
        String key = "dish" + "_" + categoryId.toString();
        dishDtoList = (List<DishDto>) redisTemplate.opsForValue().get(key);
        if (dishDtoList!=null){
            return Message.success(dishDtoList);
        }

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getStatus,1);
        dishLambdaQueryWrapper.eq(categoryId!=null,Dish::getCategoryId,categoryId);
        dishLambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = iDishService.list(dishLambdaQueryWrapper);
        dishDtoList = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId1 = item.getCategoryId();
            Category category = iCategoryService.getById(categoryId1);
            if (category!=null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId,id);
            List<DishFlavor> flavors = iDishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDto.setFlavors(flavors);
            return dishDto;

        }).collect(Collectors.toList());
//        将数据缓存进redis
        redisTemplate.opsForValue().set(key,dishDtoList,1, TimeUnit.HOURS);
        return Message.success(dishDtoList);
    }


    @PutMapping
    public Message<String> updata(@RequestBody DishDto dishDto){

        iDishService.updateWithFlavor(dishDto);
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);

        Long categoryId = dishDto.getCategoryId();
        String key = "dish_" + categoryId.toString() + "1";
        redisTemplate.delete(key);
        return Message.success("更新成功");


    }
    @DeleteMapping
    public Message<String> delete(Long ids){
        iDishService.removeById(ids);
        return Message.success("删除成功");
    }

}
