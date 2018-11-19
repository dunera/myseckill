package com.dunera.seckill.interceptor;

import com.dunera.seckill.pojo.User;
import com.dunera.seckill.utils.SessionUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lyx
 * @date 2018/11/18
 */
@Configuration
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = SessionUtil.getUserSession(request);
        if (user == null) {
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
    }
}
