package com.wjy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author : 王金云
 * @create 2022/7/14 11:29
 */
@Slf4j
@EnableCaching  //开启spring cache 注解方式的缓存功能
@SpringBootApplication

@EnableTransactionManagement
public class DdiningApplication {
    public static void main(String[] args) {
        SpringApplication.run(DdiningApplication.class);
        log.info("项目启动成功");
    }
}
