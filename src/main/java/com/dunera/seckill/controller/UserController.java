package com.dunera.seckill.controller;

import com.dunera.seckill.dto.UserLoginDto;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.UserService;
import com.dunera.seckill.utils.MD5;
import com.dunera.seckill.utils.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
public class UserController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login.html")
    public ModelAndView index(Integer page) {
        if (page == null) {
            page = 0;
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("page", page);
        modelAndView.addObject("user", new UserLoginDto());
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") UserLoginDto userLoginDto) {
        if (userLoginDto == null || StringUtils.isEmpty(userLoginDto.getUserName()) || StringUtils.isEmpty(userLoginDto.getPassword())) {
            return new ModelAndView("redirect:/login.html", "error", "用户名或密码不能为空。");
        }
        boolean valid = userService.checkPassword(userLoginDto.getUserName(), userLoginDto.getPassword());
        if (!valid) {
            return new ModelAndView("redirect:/login.html", "error", "用户名或密码错误。");
        }
        User user = userService.getUserByUserName(userLoginDto.getUserName());
        SessionUtil.setUserSession(request, user);
        return new ModelAndView("redirect:/seckill/list.html");
    }

    @RequestMapping(value = "/logout.html")
    public String logout() {
        SessionUtil.removeUserSession(request);
        return "redirect:/seckill/list.html";
    }

    @RequestMapping(value = "/register")
    public ModelAndView checkRegister(UserLoginDto userDto) {

        logger.info("username:{},password:{}", userDto.getUserName(), userDto.getPassword());
        String userName = userDto.getUserName();
        String password = userDto.getPassword();
        String confirmPassword = userDto.getConfirmPassword();
        String phoneNumber = userDto.getPhoneNumber();
        if (!password.equals(confirmPassword)) {
            ModelAndView modelAndView = new ModelAndView("redirect:/login.html");
            modelAndView.addObject("error", "两次输入的密码不一致");
            return modelAndView;
        }

        boolean isValidUser = userService.checkUserNameIllegal(userName);
        if (!isValidUser) {
            ModelAndView modelAndView = new ModelAndView("redirect:/login.html");
            modelAndView.addObject("error", "用户名已经存在");
            modelAndView.addObject("page", 1);
            return modelAndView;
        }
        userService.addUser(userName, MD5.getMD5(confirmPassword), phoneNumber, 0);
        return new ModelAndView("redirect:/login.html", "message", "注册成功");
    }
}