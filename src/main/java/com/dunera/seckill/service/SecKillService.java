package com.dunera.seckill.service;

import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillInfo;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.vo.SecKillGoodDetailVo;

import java.util.List;

/**
 * @author lyx
 * @date 2018/11/16
 */
public interface SecKillService {

    /**
     * 查询全部的秒杀商品列表
     */
    List<SecKillInfo> getSecKillInfos();

    /**
     * 查询单个秒杀商品详情
     */
    SecKillGoodDetailVo getSecKillGoodDetail(Long secKillId);

    /**
     * 秒杀业务逻辑
     */
    SecKillOrder doSecKill(User user, Long secKillGoodId) throws GlobalException;

    /**
     * 创建秒杀订单
     */
    SecKillOrder createSecKillOrder(User user, SecKillInfo secKillInfo);

    /**
     * 获取秒杀订单
     */
    List<SecKillOrder> getSecKillOrders(User user);

    /**
     * 根据用户id和商品id获取秒杀订单
     */
    SecKillOrder getSecKillOrder(Long userId, Long goodsId);

    /**
     * 校验秒杀订单状态
     */
    boolean validSecKillStatus(Long secKillGoodId);

    /**
     * 获取秒杀状态
     */
    int getSecKillStatus(SecKillGoodDetailVo detailVo);

    /**
     * 获取秒杀剩余时间
     */
    int getRemainSeconds(SecKillGoodDetailVo detailVo);

    /**
     * 更新库存缓存
     */
    void updateStockCache();

    /**
     * 更新列表缓存
     */
    void updateSecKillInfos();
}
