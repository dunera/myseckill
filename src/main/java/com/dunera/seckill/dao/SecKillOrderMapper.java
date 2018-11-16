package com.dunera.seckill.dao;

import com.dunera.seckill.pojo.SecKillOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface SecKillOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecKillOrder record);

    int insertSelective(SecKillOrder record);

    SecKillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecKillOrder record);

    int updateByPrimaryKey(SecKillOrder record);
}