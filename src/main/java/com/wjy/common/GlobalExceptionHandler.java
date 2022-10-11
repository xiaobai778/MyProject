package com.wjy.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @Author : 王金云
 * @create 2022/7/20 19:26
 * 全局异常处理
 * 底层通过代理   aop
 */
@Slf4j
//aop异常通知
@ControllerAdvice(annotations = {RestController.class,Controller.class})
@ResponseBody

public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Message<String> exceptionhandler(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        if (ex.getMessage().contains("Duplicate entry")){
            String[] split = ex.getMessage().split(" ");
            String msg = split[2] + "已经存在";
            return Message.error(msg);
        }

        return Message.error("SQL异常，添加失败");

    }
//    SQLIntegrityConstraintViolationException



    @ExceptionHandler(CustomException.class)
    public Message<String> exceptionhandler(CustomException ex){
        log.info(ex.getMessage());

        return Message.error(ex.getMessage()+"删除菜品失败");

    }
}
