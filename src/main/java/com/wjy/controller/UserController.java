package com.wjy.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import com.wjy.pojo.User;
import com.wjy.service.IUserService;
import com.wjy.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author : ηιδΊ
 * @create 2022/8/1 22:47
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public Message<User> login(@RequestBody Map map, HttpSession session){
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        Object sessionAttribute = redisTemplate.opsForValue().get(phone);

//        Object sessionAttribute = session.getAttribute(phone);
        if (sessionAttribute != null && sessionAttribute.equals(code)) {
            LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userLambdaQueryWrapper.eq(User::getPhone,phone);
            User user = iUserService.getOne(userLambdaQueryWrapper);
            if (user==null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                iUserService.save(user);
            }
            session.setAttribute("userId",user.getId());
            log.info(user.getId().toString());
            redisTemplate.delete(phone);
            return Message.success(user);
        }
        return Message.error("η»ε½ε€±θ΄₯");
    }

    @PostMapping("loginout")
    public Message<String> loginout(){
        BaseContext.setCurrentId(null);
        return Message.success("ιεΊζε");
    }



    @PostMapping("/sendMsg")
    public Message<String> sendMsg(@RequestBody User user,HttpSession session ) {
        log.info("sengmsg ζΉζ³");
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("ιͺθ―η δΈΊοΌ{}",code);
//            session.setAttribute(phone,code);

//            ε°ιͺθ―η ε­ε₯redisδΈ­οΌεΉΆδΈθ?Ύη½?ζΆιΏδΈΊδΊει
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return Message.success("ειζε");


        }
        return Message.success("ειε€±θ΄₯");
    }
}
