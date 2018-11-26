package com.dunera.seckill.common.rabbitmq;

import com.dunera.seckill.pojo.User;

/**
 * @author lyx
 * @date 2018/11/22
 */
public class SeckillMessage {

    private User user;
    private long goodsId;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }
}
