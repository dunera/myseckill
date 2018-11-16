package com.dunera.seckill.controller;

import com.dunera.seckill.pojo.SecKillDetail;
import com.dunera.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author lyx
 * @date 2018/11/12
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

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
            return "redirect:/seckill/list";
        }

        SecKillDetail seckillGood = seckillService.getSecKillDetail(seckillGoodId);

        if (seckillGood == null) {
            return "forward:/seckill/list";
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
}