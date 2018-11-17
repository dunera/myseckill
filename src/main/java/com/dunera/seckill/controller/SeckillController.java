package com.dunera.seckill.controller;

import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillDetail;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SeckillService;
import com.dunera.seckill.utils.SessionUtil;
import com.dunera.seckill.vo.SecKillGoodDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String list(Model model) {
        List<SecKillDetail> list = seckillService.getSecKillDetails();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/detail/{seckillGoodId}", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillGoodId") Long seckillGoodId, Model model) {
        if (seckillGoodId == null) {
            return "redirect:/seckill/list.html";
        }

        User user = SessionUtil.getUserSession(httpServletRequest);
        model.addAttribute("user", user);
        SecKillGoodDetailVo seckillGood = seckillService.getSecKillGoodDetail(seckillGoodId);

        if (seckillGood == null) {
            return "forward:/seckill/list.html";
        }

        long startTime = seckillGood.getStartTime().getTime();
        long endTime = seckillGood.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int seckillStatus = 0;
        int remainSeconds = 0;
        //秒杀还没开始，倒计时
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
            //秒杀已经结束
        } else if (now > endTime) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {//秒杀进行中
            seckillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("seckillStatus", seckillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        model.addAttribute("seckillGood", seckillGood);
        return "detail";
    }


    @RequestMapping(value = "/doSecKill.html", method = RequestMethod.POST)
    public String doSecKill(@RequestParam("seckillGoodId") Long seckillGoodId, User user, Model model) {
        if (user == null || user.getUserId() == null) {
            user = SessionUtil.getUserSession(httpServletRequest);
        }
        try {
            SecKillOrder order = seckillService.doSecKill(user, seckillGoodId);
            model.addAttribute("order", order);
            return "myOrders";
        } catch (GlobalException e) {
            model.addAttribute("message", e.getCodeMessage().getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/myOrders.html", method = RequestMethod.GET)
    public String myOrders(User user, Model model) {
        if (user == null || user.getUserId() == null) {
            user = SessionUtil.getUserSession(httpServletRequest);
        }
        model.addAttribute("user", user);
        List<SecKillOrder> orders = seckillService.getSecKillOrders(user);
        model.addAttribute("orders", orders);
        return "myOrders";
    }

}