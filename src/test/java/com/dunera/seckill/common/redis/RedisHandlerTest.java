package com.dunera.seckill.common.redis;

import com.dunera.seckill.SeckillApplication;
import com.dunera.seckill.config.RedisProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author lyx
 * @date 2018/11/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeckillApplication.class)
public class RedisHandlerTest {

    @Autowired
    private RedisProperties properties;

    @Test
    public void set() {
        System.out.println(properties.getHost());
    }
}