package com.wjy.filter;

import com.alibaba.fastjson.JSON;
import com.wjy.common.BaseContext;
import com.wjy.common.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : 王金云
 * @create 2022/7/14 18:54
 */
@Slf4j
@WebFilter(filterName = "loginChrckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        获取URI
        String requestURI = request.getRequestURI();
        String[] urls = {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
//        判断请求是否处理
        boolean check = check(urls, requestURI);

        if (check){
            chain.doFilter(request,response);
            return;
        }
//        已登录 放行
        if (request.getSession().getAttribute("employee")!=null){
//            long id = Thread.currentThread().getId();
//            log.info("线程ID为{}",id);
            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            chain.doFilter(request,response);
            return;
        }
        //        已登录 放行（移动端登录）
        if (request.getSession().getAttribute("userId")!=null){
//            long id = Thread.currentThread().getId();
//            log.info(".getAttribute(\"user\"){}",request.getSession().getAttribute("userId"));
            Long userId = Long.valueOf(request.getSession().getAttribute("userId").toString());
            BaseContext.setCurrentId(userId);
            log.info("8888***8888888"+userId);


            chain.doFilter(request,response);
            return;
        }
//        未登录处理
        response.getWriter().write(JSON.toJSONString(Message.error("NOTLOGIN")));
        return;

//        log.info("拦截器拦截请求：{}",request.getRequestURI());
//        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }



}
