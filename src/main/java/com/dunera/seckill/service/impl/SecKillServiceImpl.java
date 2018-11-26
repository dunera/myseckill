package com.dunera.seckill.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.dunera.seckill.common.redis.RedisHandler;
import com.dunera.seckill.dao.SecKillInfoMapper;
import com.dunera.seckill.dao.SecKillOrderMapper;
import com.dunera.seckill.exception.ErrorMessage;
import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillInfo;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SecKillService;
import com.dunera.seckill.vo.SecKillGoodDetailVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author lyx
 * @date 2018/11/16
 */
@Service
public class SecKillServiceImpl implements SecKillService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public static final int ACTIVE = 1;

    @Autowired
    private SecKillInfoMapper secKillInfoMapper;

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;

    @Autowired
    private RedisHandler redisHandler;

    public static final String STOCK_COUNT = "stock_count:";
    public static final String SEC_KILL_INFO = "sec_kill_info:";
    public static final String SEC_KILL_GOOD_DETAIL = "sec_kill_good_detail:";

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public SecKillOrder doSecKill(User user, Long secKillGoodId) throws GlobalException {

        SecKillOrder secKillOrder = getSecKillOrder(user.getUserId(), secKillGoodId);

        if (secKillOrder != null) {
            throw new GlobalException(ErrorMessage.SEK_REPEAT_ORDER);
        }
        boolean success = decrStock(secKillGoodId);
        if (!success) {
            throw new GlobalException(ErrorMessage.SEK_STOCK_NOT_ENOUGH);
        }
        try {
            SecKillInfo secKillInfo = secKillInfoMapper.selectByPrimaryKey(secKillGoodId);
            SecKillOrder order = createSecKillOrder(user, secKillInfo);
            logger.info("当前mysql-secKillInfo:stock:{}", secKillInfo.getStock());
            updateSecKillInfosCache();
            return order;
        } catch (Exception e) {
            throw new GlobalException(ErrorMessage.SEK_ENDED);
        }
    }

    @Override
    public SecKillOrder createSecKillOrder(User user, SecKillInfo secKillInfo) {
        SecKillOrder order = new SecKillOrder();
        order.setGoodsId(secKillInfo.getGoodsId());
        order.setSeckillId(secKillInfo.getId());
        order.setState(SecKillOrder.STATE_SUCCESS);
        order.setUserId(user.getUserId());
        order.setCreateTime(new Date());
        secKillOrderMapper.insert(order);
        return order;
    }

    @Override
    public boolean decrStock(Long goodsId) {
        Integer stock = redisHandler.get(STOCK_COUNT, String.valueOf(goodsId), Integer.class);
        logger.info("redis--count:{}", stock);
        if (stock <= 0) {
            return false;
        }
        int ret = secKillInfoMapper.decrStock(goodsId);
        if (ret > 0) {
            redisHandler.decr(STOCK_COUNT, String.valueOf(goodsId));
        }
        return ret > 0;
    }

    @Override
    public SecKillGoodDetailVo getSecKillGoodDetail(Long seckillId) {
        SecKillGoodDetailVo vo = redisHandler.get(SEC_KILL_GOOD_DETAIL, String.valueOf(seckillId), SecKillGoodDetailVo.class);
        Optional<SecKillGoodDetailVo> optional = Optional.ofNullable(vo);
        if (optional.isPresent()) {
            return vo;
        }
        vo = secKillInfoMapper.selectSecKillGoodDetails(seckillId);
        redisHandler.set(SEC_KILL_GOOD_DETAIL, String.valueOf(seckillId), vo);
        return vo;
    }

    @Override
    public List<SecKillInfo> getSecKillInfos() {
        return JSONArray.parseArray(redisHandler.get("", SEC_KILL_INFO), SecKillInfo.class);
    }

    @Override
    public List<SecKillOrder> getSecKillOrders(User user) {
        return secKillOrderMapper.selectUserOrders(user);
    }

    @Override
    public SecKillOrder getSecKillOrder(Long userId, Long goodsId) {
        return secKillOrderMapper.getOrderByUserAndGood(userId, goodsId);
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

    @Override
    public void afterPropertiesSet() throws Exception {
        updateStockCache();
        updateSecKillInfosCache();
    }

    @Override
    public void updateStockCache() {
        secKillInfoMapper.selectSecKillInfos(ACTIVE).forEach(e -> redisHandler.set(STOCK_COUNT, String.valueOf(e.getId()), e.getStock()));
    }

    @Override
    public void updateSecKillInfosCache() {
        List<SecKillInfo> secKillInfos = secKillInfoMapper.selectSecKillInfos(ACTIVE);
        redisHandler.set(SEC_KILL_INFO, secKillInfos);
    }

}