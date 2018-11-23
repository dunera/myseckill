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
     * 设置值
     */
    public String set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从redis连接池获取redis实例
     */
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 键是否存在于redis中
     */
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 键是否存在于redis中
     */
    public boolean remove(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
