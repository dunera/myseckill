package com.dunera.seckill.dao;

import com.dunera.seckill.pojo.SecKillOrder;
import com.dunera.seckill.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecKillOrderMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SecKillOrder record);

    int insertSelective(SecKillOrder record);

    SecKillOrder selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecKillOrder record);

    int updateByPrimaryKey(SecKillOrder record);

    List<SecKillOrder> selectUserOrders(User user);
}