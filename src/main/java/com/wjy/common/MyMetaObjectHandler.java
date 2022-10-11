package com.wjy.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author : 王金云
 * @create 2022/7/24 12:57
 * 自定义元数据处理器
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId());
//        log.info(BaseContext.getCurrentId().toString());
        metaObject.setValue("updateUser",BaseContext.getCurrentId());

        log.info("插入 自动填充");
        log.info(metaObject.toString());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("更新 自动填充");
        log.info(metaObject.toString());
//        metaObject.setValue("createTime",LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
//        metaObject.setValue("createUser",new Long(1));
        metaObject.setValue("updateUser",BaseContext.getCurrentId());
        long id = Thread.currentThread().getId();
        log.info("线程ID为{}",id);
    }
}
