package com.dunera.seckill.service.impl;

import com.dunera.seckill.dao.SecKillInfoMapper;
import com.dunera.seckill.dao.SecKillOrderMapper;
import com.dunera.seckill.exception.ErrorMessage;
import com.dunera.seckill.exception.GlobalException;
import com.dunera.seckill.pojo.SecKillInfo;
import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SecKillService;
import com.dunera.seckill.vo.SecKillGoodDetailVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lyx
 * @date 2018/11/16
 */
@Service
public class SecKillServiceImpl implements SecKillService, InitializingBean {

    public static final int ACTIVE = 1;

    @Autowired
    private SecKillInfoMapper secKillInfoMapper;

    @Autowired
    private SecKillOrderMapper secKillOrderMapper;

    private Map<Long, Integer> stockCountCache = new ConcurrentHashMap<>();

    private List<SecKillInfo> secKillInfoCache = new CopyOnWriteArrayList<>();

    @Override
    @Transactional(rollbackFor = GlobalException.class)
    public SecKillOrder doSecKill(User user, Long secKillGoodId) throws GlobalException {
        SecKillInfo secKillInfo = secKillInfoMapper.selectByPrimaryKey(secKillGoodId);
        SecKillOrder order;
        try {
            order = createSecKillOrder(user, secKillInfo);
            //减库存
            secKillInfo.setStock(secKillInfo.getStock() - 1);
            stockCountCache.computeIfPresent(secKillGoodId, (k, v) -> v--);
            secKillInfoMapper.updateByPrimaryKey(secKillInfo);
            updateSecKillInfos();
        } catch (Exception e) {
            throw new GlobalException(ErrorMessage.SEK_ENDED);
        }
        return order;
    }

    @Override
    public boolean validSecKillStatus(Long secKillGoodId) throws GlobalException {
        int stock = stockCountCache.get(secKillGoodId);
        if (stock <= 0) {
            throw new GlobalException(ErrorMessage.SEK_STOCK_NOT_ENOUGH);
        }
        return true;
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
        updateSecKillInfos();
        return order;
    }

    @Override
    public SecKillGoodDetailVo getSecKillGoodDetail(Long seckillId) {
        return secKillInfoMapper.selectSecKillGoodDetails(seckillId);
    }

    @Override
    public List<SecKillInfo> getSecKillInfos() {
        return secKillInfoCache;
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
        updateSecKillInfos();
    }

    @Override
    public void updateStockCache() {
        secKillInfoMapper.selectSecKillInfos(ACTIVE).forEach(e -> stockCountCache.put(e.getId(), e.getStock()));
    }

    @Override
    public void updateSecKillInfos() {
        List<SecKillInfo> secKillInfos = secKillInfoMapper.selectSecKillInfos(ACTIVE);
        Optional<List<SecKillInfo>> infos = Optional.ofNullable(secKillInfos);
        infos.ifPresent(s -> secKillInfoCache.addAll(s));
        secKillInfoCache = infos.orElse(new CopyOnWriteArrayList<>());
    }


}
