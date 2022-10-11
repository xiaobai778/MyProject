package com.wjy.common;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author : 王金云
 * @create 2022/7/14 12:17
 */
    @Data
    public class Message<T>{

        private Integer code; //编码：1成功，0和其它数字为失败

        private String msg; //错误信息

        private T data; //数据

        private Map map = new HashMap(); //动态数据

        public static <T> Message<T> success(T object) {
            Message<T> r = new Message<T>();
            r.data = object;
            r.code = 1;
            return r;
        }

        public static <T> Message<T> error(String msg) {
            Message r = new Message();
            r.msg = msg;
            r.code = 0;
            return r;
        }

        public Message<T> add(String key, Object value) {
            this.map.put(key, value);
            return this;
        }


    }
