package com.dunera.seckill.service.impl;

import com.dunera.seckill.dao.SecKillDetailMapper;
import com.dunera.seckill.dao.SecKillOrderMapper;
import com.dunera.seckill.exception.CodeMessage;
import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillDetail;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SeckillService;
import com.dunera.seckill.vo.SecKillGoodDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author lyx
 * @date 2018/11/16
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SecKillDetailMapper secKillDetailMapper;

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;

    @Override
    @Transactional
    public  SecKillOrder doSecKill(User user, Long seckillGoodId) throws GlobalException {

        /*
         * 秒杀流程
         * 1. 查询秒杀是否有效，是否超时
         * 2. 查询库存
         * 3. 加入队列
         * 4. 处理队列中秒杀订单
         * 5. 秒杀成功，返回成功，失败返回秒杀结束
         */
        SecKillDetail secKillDetail = secKillDetailMapper.selectByPrimaryKey(seckillGoodId);
        if (!secKillDetail.getIsActive()) {
            throw new GlobalException(CodeMessage.GOOD_UNACTIVE);
        }

        if (secKillDetail.getEndTime().before(new Date())) {
            throw new GlobalException(CodeMessage.SEK_END);
        }

        if (secKillDetail.getStock() <= 0) {
            throw new GlobalException(CodeMessage.SEK_STOCK_NOT_ENOUGH);
        }

        SecKillOrder order = new SecKillOrder();
        try {
            //生成新订单
            order.setGoodsId(secKillDetail.getGoodsId());
            order.setSeckillId(secKillDetail.getId());
            order.setState(SecKillOrder.STATE_SUCCESS);
            order.setUserId(user.getUserId());
            order.setCreateTime(new Date());
            secKillOrderMapper.insert(order);
            //减库存
            secKillDetail.setStock(secKillDetail.getStock() - 1);
            secKillDetailMapper.updateByPrimaryKey(secKillDetail);
        } catch (DuplicateKeyException e) {
            throw new GlobalException(CodeMessage.SEK_REPEAT_ORDER);
        }
        return order;
    }

    @Override
    public SecKillGoodDetailVo getSecKillGoodDetail(Long seckillId) {
        return secKillDetailMapper.selectSecKillGoodDetails(seckillId);
    }

    @Override
    public List<SecKillDetail> getSecKillDetails() {
        return secKillDetailMapper.selectValidSecKills();
    }

    @Override
    public List<SecKillOrder> getSecKillOrders(User user) {
        return secKillOrderMapper.selectUserOrders(user);
    }

    @Override
    public int getSecKillStatus(SecKillGoodDetailVo detailVo) {
        long startTime = detailVo.getStartTime().getTime();
        long endTime = detailVo.getEndTime().getTime();
        long now = System.currentTimeMillis();
        //秒杀还没开始，倒计时
        int status = 0;
        if (now > endTime) {
            status = 2;
            //秒杀进行中
        } else if (startTime < now && now < endTime) {
            status = 1;
        }
        return status;
    }

    @Override
    public int getRemainSeconds(SecKillGoodDetailVo detailVo) {
        long startTime = detailVo.getStartTime().getTime();
        long endTime = detailVo.getEndTime().getTime();
        long now = System.currentTimeMillis();

        int remainSeconds = 0;
        //秒杀还没开始，倒计时
        if (now < startTime) {
            remainSeconds = (int) ((startTime - now) / 1000);
            //秒杀已经结束
        } else if (startTime < now && now < endTime) {
            remainSeconds = (int) ((endTime - now) / 1000);
        } else {//秒杀进行中
            remainSeconds = -1;
        }
        return remainSeconds;
    }
}
