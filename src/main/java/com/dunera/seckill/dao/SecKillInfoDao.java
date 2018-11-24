package com.dunera.seckill.dao;

import com.dunera.seckill.common.redis.RedisHandler;
import com.dunera.seckill.pojo.SecKillInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author lyx
 * @date 2018/11/23
 */
@Repository
public class SecKillInfoDao {

    @Autowired
    private RedisHandler redisHandler;

    public static final String SEC_KILL_INFO = "sec_kill_info:";

    int insert(SecKillInfo record) {
        boolean success = redisHandler.set(SEC_KILL_INFO, String.valueOf(record.getId()), record);
        if (success) {
            return 1;
        }
        return 0;
    }


}
