package com.dunera.seckill.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author lyx
 * @date 2018/11/22
 */
@Service
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 从redis连接池获取redis实例
     */
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
