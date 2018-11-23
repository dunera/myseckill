package com.dunera.seckill.controller;

import com.dunera.seckill.exception.ErrorMessage;
import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillInfo;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SecKillService;
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
public class SecKillController {

    @Autowired
    HttpServletRequest httpServletRequest;

    @Autowired
    private SecKillService seckillService;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public String list(Model model) {
        seckillService.updateStockCache();
        List<SecKillInfo> list = seckillService.getSecKillInfos();
        model.addAttribute("list", list);
        return "list";
    }

    @RequestMapping(value = "/detail/{secKillGoodId}", method = RequestMethod.GET)
    public String detail(@PathVariable("secKillGoodId") Long secKillGoodId, Model model) {
        if (secKillGoodId == null) {
            return "redirect:/seckill/list.html";
        }

        User user = SessionUtil.getUserSession(httpServletRequest);
        model.addAttribute("user", user);
        SecKillGoodDetailVo secKillGoodDetail = seckillService.getSecKillGoodDetail(secKillGoodId);

        if (secKillGoodDetail == null) {
            return "forward:/seckill/list.html";
        }
        int secKillStatus = seckillService.getSecKillStatus(secKillGoodDetail);
        int remainSeconds = seckillService.getRemainSeconds(secKillGoodDetail);
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillGood", secKillGoodDetail);
        return "detail";
    }

    @RequestMapping(value = "/doSecKill.html", method = RequestMethod.POST)
    public String doSecKill(@RequestParam("secKillGoodId") Long secKillGoodId, User user, Model model) {
        if (user == null || user.getUserId() == null) {
            user = SessionUtil.getUserSession(httpServletRequest);
        }
        try {
            SecKillOrder secKillOrder = seckillService.getSecKillOrder(user.getUserId(), secKillGoodId);
            if (secKillOrder != null) {
                throw new GlobalException(ErrorMessage.SEK_REPEAT_ORDER);
            }

            boolean stockEnough = seckillService.validSecKillStatus(secKillGoodId);
            if (!stockEnough) {
                throw new GlobalException(ErrorMessage.SEK_STOCK_NOT_ENOUGH);
            }
            SecKillOrder order = seckillService.doSecKill(user, secKillGoodId);
            model.addAttribute("user", user);
            model.addAttribute("order", order);
            return "redirect:/seckill/orders.html";
        } catch (GlobalException e) {
            model.addAttribute("message", e.getErrorMessage().getMessage());
            return "error";
        }
    }

    @RequestMapping(value = "/orders.html", method = RequestMethod.GET)
    public String orders(User user, Model model) {
        if (user == null || user.getUserId() == null) {
            user = SessionUtil.getUserSession(httpServletRequest);
        }
        model.addAttribute("user", user);
        List<SecKillOrder> orders = seckillService.getSecKillOrders(user);
        model.addAttribute("orders", orders);
        return "orders";
    }

}