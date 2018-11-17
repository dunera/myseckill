package com.dunera.seckill.service;

import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillDetail;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.vo.SecKillGoodDetailVo;

import java.util.List;

/**
 * @author lyx
 * @date 2018/11/16
 */
public interface SeckillService {

    /**
     * 查询全部的秒杀商品列表
     */
    List<SecKillDetail> getSecKillDetails();

    /**
     * 查询单个秒杀商品详情
     */
    SecKillGoodDetailVo getSecKillGoodDetail(Long seckillId);

    /**
     * 秒杀业务逻辑
     */
    SecKillOrder doSecKill(User user, Long seckillGoodId) throws GlobalException;

    /**
     * 获取秒杀订单
     */
    List<SecKillOrder> getSecKillOrders(User user);
}
