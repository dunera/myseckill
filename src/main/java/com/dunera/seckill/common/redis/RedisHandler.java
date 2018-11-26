package com.dunera.seckill.common.redis;

import com.dunera.seckill.utils.SerializeUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author lyx
 * @date 2018/11/22
 */
@Service
public class RedisHandler {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 设置值
     */
    public <T> boolean set(String prefix, String key, T value) {
        String relKey = key;
        if (!Strings.isNullOrEmpty(prefix)) {
            relKey = prefix + key;
        }
        return set(relKey, value);
    }

    /**
     * 设置值
     */
    public <T> boolean set(String key, T value) {
        try (Jedis jedis = jedisPool.getResource()) {
            String valueStr = SerializeUtil.beanToString(value);
            if (!Strings.isNullOrEmpty(valueStr)) {
                jedis.set(key, valueStr);
                return true;
            }
        }
        return false;
    }

    /**
     * 从redis连接池获取redis实例
     */
    public <T> T get(String prefix, String key, Class<T> clazz) {
        String relKey = key;
        if (!Strings.isNullOrEmpty(prefix)) {
            relKey = prefix + key;
        }
        String objStr = get(relKey);
        return SerializeUtil.stringToBean(objStr, clazz);
    }

    /**
     * 从redis连接池获取redis实例
     */
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * 从redis连接池获取redis实例
     */
    public String get(String prefix, String key) {
        String relKey = key;
        if (!Strings.isNullOrEmpty(prefix)) {
            relKey = prefix + key;
        }
        return get(relKey);
    }

    /**
     * 判断键值是否存在
     */
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    /**
     * 移除值
     */
    public boolean remove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            long ret = jedis.del(key);
            return ret > 0;
        }
    }


    /**
     * redis incr
     * 如果 key 不存在，那么 key 的值会先被初始化为0 ，然后再执行 incr 操作
     */
    public Long incr(String prefix, String key) {
        String relKey = key;
        if (!Strings.isNullOrEmpty(prefix)) {
            relKey = prefix + key;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.incr(relKey);
        }
    }

    /**
     * redis decr
     */
    public Long decr(String prefix, String key) {
        String relKey = key;
        if (!Strings.isNullOrEmpty(prefix)) {
            relKey = prefix + key;
        }
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.decr(relKey);
        }
    }

    public Jedis getResource() {
        return jedisPool.getResource();
    }

    public void closeResource(Jedis jedis) {
        // 将连接放回池中
        if (jedis != null) {
            jedis.close();
        }
    }

}
