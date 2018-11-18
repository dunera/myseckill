package com.dunera.seckill.utils;

import com.dunera.seckill.pojo.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyx
 * @date 2018/11/12
 */
public class SessionUtil {

    private final static String USER_SESSION = "user";

    public static User getUserSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER_SESSION);
    }

    public static void setUserSession(HttpServletRequest request, User user) {
        request.getSession().setAttribute(USER_SESSION, user);
    }

    public static void removeUserSession(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_SESSION);
    }

}
