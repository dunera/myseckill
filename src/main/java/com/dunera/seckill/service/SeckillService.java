package com.dunera.seckill.service;

import com.dunera.seckill.pojo.SecKillDetail;

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
     * 查询单个秒杀商品信息
     */
    SecKillDetail getSecKillDetail(Long seckillId);

}
