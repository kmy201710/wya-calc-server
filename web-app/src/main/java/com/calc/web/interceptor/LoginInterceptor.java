package com.calc.web.interceptor;

import com.calc.web.constant.CacheConstant;
import com.calc.web.utils.EmptyUtils;
import com.calc.web.utils.SpringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/qsbbl/article/details/98441817
 * 拦截器，登录检查
 */
public class LoginInterceptor implements HandlerInterceptor {

    private static RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");

    /**
     * 在请求被处理之前调用
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取所有请求头名称  https://blog.csdn.net/chucuo8079/article/details/100666794?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            //根据名称获取请求头的值
//            String value = request.getHeader(name);
//            System.out.println(name+"---"+value);
//        }
        // 检查每个到来的请求对应的session域中是否有登录标识
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("requestURL = " + requestURL);

        String token = request.getHeader("x-access-token");
        String redisKey = CacheConstant.CACHE_KEY_LOGIN_TOKEN_PHONE + token;
        String loginName = redisTemplate.opsForValue().get(redisKey);
        if (EmptyUtils.isEmpty(loginName)) {
            // 未登录，重定向到登录页
//            response.sendRedirect("/");
            System.out.println("用户未登录");
            return false;
        }
        redisTemplate.expire(redisKey, CacheConstant.CACHE_TIME_LOGIN, TimeUnit.SECONDS);
        String userName = loginName;
        System.out.println("当前用户已登录，登录的用户名为： " + userName);
        return true;
    }

    /**
     * 在请求被处理后，视图渲染之前调用
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在整个请求结束后调用
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}