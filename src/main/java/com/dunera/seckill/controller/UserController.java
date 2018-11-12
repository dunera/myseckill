package com.dunera.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
public class UserController {

    @RequestMapping(value = "/login.html")
    public ModelAndView showLoginPage(Integer page) {
        if (page == null) {
            page = 0;
        }
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }
}
