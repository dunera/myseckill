package com.dunera.seckill.controller;

import com.dunera.seckill.pojo.User;
import com.dunera.seckill.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
public class IndexController {

    @Autowired
    HttpServletRequest request;

    @RequestMapping(value = {"/", "/index.html","/index"})
    public ModelAndView showIndexPage() {
        User user = SessionUtil.getUserSession(request);
        ModelAndView modelAndView = new ModelAndView("redirect:/seckill/list.html");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
