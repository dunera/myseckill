package com.dunera.seckill.dao;

import com.dunera.seckill.pojo.SecKillInfo;
import com.dunera.seckill.vo.SecKillGoodDetailVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecKillInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(SecKillInfo record);

    int insertSelective(SecKillInfo record);

    SecKillInfo selectByPrimaryKey(Long id);

    SecKillGoodDetailVo selectSecKillGoodDetails(Long id);

    int updateByPrimaryKeySelective(SecKillInfo record);

    int updateByPrimaryKey(SecKillInfo record);

    List<SecKillInfo> selectSecKillInfos(int valid);

    int decrStock(Long goodsId);
}