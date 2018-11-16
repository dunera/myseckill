package com.dunera.seckill.service.impl;

import com.dunera.seckill.dao.SecKillDetailMapper;
import com.dunera.seckill.pojo.SecKillDetail;
import com.dunera.seckill.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lyx
 * @date 2018/11/16
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private SecKillDetailMapper secKillDetailMapper;

    @Override
    public List<SecKillDetail> getSecKillDetails() {
        return secKillDetailMapper.selectValidSecKills();
    }

    @Override
    public SecKillDetail getSecKillDetail(Long seckillId) {
        return secKillDetailMapper.selectByPrimaryKey(seckillId);
    }
}
