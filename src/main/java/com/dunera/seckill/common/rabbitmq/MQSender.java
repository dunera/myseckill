package com.dunera.seckill.common.rabbitmq;

import com.dunera.seckill.utils.SerializeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lyx
 * @date 2018/11/22
 */
@Service
public class MQSender {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendMessage(String queue, Object message) {
        String msg = SerializeUtil.beanToString(message);
        logger.debug("send message:" + msg);
        amqpTemplate.convertAndSend(queue, msg);
    }
}
