package com.wjy.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author : 王金云
 * @create 2022/7/14 12:18
 */
    @Data
    public class Employee implements Serializable {

        private static final long serialVersionUID = 1L;

        private Long id;

        private String username;

        private String name;

        private String password;

        private String phone;

        private String sex;

        private String idNumber;

        private Integer status;

        private LocalDateTime createTime;

        private LocalDateTime updateTime;

        @TableField(fill = FieldFill.INSERT)
        private Long createUser;

        @TableField(fill = FieldFill.INSERT_UPDATE)
        private Long updateUser;

}
