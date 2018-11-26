package com.dunera.seckill.common.rabbitmq;

import com.dunera.seckill.config.RabbitMQConfig;
import com.dunera.seckill.pojo.User;
import com.dunera.seckill.service.SecKillService;
import com.dunera.seckill.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyx
 * @date 2018/11/22
 */
@Service
public class MQReceiver {

    private static Logger logger = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    SecKillService seckillService;

    @RabbitListener(queues = RabbitMQConfig.SECKILL_QUEUE)
    public void receive(String message) {
        logger.info("receive message:" + message);
        SeckillMessage m = SerializeUtil.stringToBean(message, SeckillMessage.class);
        User user = m.getUser();
        long goodsId = m.getGoodsId();
        //处理生成秒杀订单
        seckillService.doSecKill(user, goodsId);
    }

}
