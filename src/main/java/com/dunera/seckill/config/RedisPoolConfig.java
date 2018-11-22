package com.dunera.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author lyx
 * @date 2018/11/22
 */
@Configuration
public class RedisPoolConfig {

    @Autowired
    RedisProperties redisProperties;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(redisProperties.getPool().getMaxIdle());
        config.setMaxTotal(redisProperties.getPool().getMaxTotal());
        config.setMinIdle(redisProperties.getPool().getMinIdle());
        config.setBlockWhenExhausted(false);
        config.setMaxWaitMillis(redisProperties.getPool().getMaxWait() * 1000);
        return new JedisPool(config, redisProperties.getHost(), redisProperties.getPort(),
                redisProperties.getTimeout() * 1000, redisProperties.getPassword(),
                redisProperties.getDatabase());
    }

}
