package com.dunera.seckill.controller;

import com.dunera.seckill.dto.UserLoginDto;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.UserService;
import com.dunera.seckill.utils.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login.html")
    public ModelAndView index(RedirectAttributes ra, String page) {
        if (page == null) {
            page = "login";
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("page", page);
        modelAndView.addObject("user", new UserLoginDto());
        modelAndView.addObject("error");
        modelAndView.addAllObjects(ra.asMap());
        return modelAndView;
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") UserLoginDto userLoginDto) {
        boolean valid = userService.checkPassword(userLoginDto.getUserName(), userLoginDto.getPassword());
        if (!valid) {
            return new ModelAndView("redirect:/login.html", "error", "用户名或密码错误。");
        }
        User user = userService.getUserByUserName(userLoginDto.getUserName());
        SessionUtil.setUserSession(request, user);
        return new ModelAndView("redirect:/index.html");
    }
}
