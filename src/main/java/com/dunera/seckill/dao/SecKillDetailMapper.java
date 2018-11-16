package com.dunera.seckill.dao;

import com.dunera.seckill.pojo.SecKillDetail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecKillDetailMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SecKillDetail record);

    int insertSelective(SecKillDetail record);

    SecKillDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SecKillDetail record);

    int updateByPrimaryKey(SecKillDetail record);

    List<SecKillDetail> selectValidSecKills();

}