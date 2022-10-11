package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wjy.common.Message;
import com.wjy.pojo.Category;
import com.wjy.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Author : 王金云
 * @create 2022/7/24 18:46
 */

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;

    @PostMapping
    public Message<String> save(@RequestBody Category category){
        iCategoryService.save(category);
        log.info("新增菜品");
        return Message.success("新增菜品成功");
    }
    @GetMapping("/page")
    public Message<Page> page(int page,int pageSize){
        log.info("{}",page);
//        分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
//        条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.orderByAsc(Category::getSort);
        iCategoryService.page(pageInfo,lambdaQueryWrapper);
        return Message.success(pageInfo);

    }

    @DeleteMapping
    public Message<String> delete(Long ids){
        iCategoryService.remove(ids);
        return Message.success("删除成功");
    }
    @PutMapping
    public Message<String> update(@RequestBody Category category){
        iCategoryService.updateById(category);
        return Message.success("修改成功");
    }
    @GetMapping("/list")
    public Message<List<Category>> lsit(Category category){
        LambdaQueryWrapper<Category> categoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        categoryLambdaQueryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        categoryLambdaQueryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = iCategoryService.list(categoryLambdaQueryWrapper);
        return Message.success(list);
    }


}

