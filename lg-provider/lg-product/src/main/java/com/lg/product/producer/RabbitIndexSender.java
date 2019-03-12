package com.lg.product.producer;

import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.product.service.TMqMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ┏┓　　　┏┓
 * ┏┛┻━━━┛┻┓
 * ┃　　　　　　　┃
 * ┃　　　━　　　┃
 * ┃　┳┛　┗┳　┃
 * ┃　　　　　　　┃
 * ┃　　　┻　　　┃
 * ┃　　　　　　　┃
 * ┗━┓　　　┏━┛
 * 　　┃　　　┃神兽保佑
 * 　　┃　　　┃代码无BUG！
 * 　　┃　　　┗━━━┓
 * 　　┃　　　　　　　┣┓
 * 　　┃　　　　　　　┏┛
 * 　　┗┓┓┏━┳┓┏┛
 * 　　　┃┫┫　┃┫┫
 * 　　　┗┻┛　┗┻┛
 *
 * @program: lg-master
 * @description:
 * @author: 徐子楼
 * @create: 2019-03-08 10:51
 **/
@Component
@Slf4j
public class RabbitIndexSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TMqMessageLogService mqMessageLogService;

    //发送消息方法调用: 构建自定义对象消息
    public void sendIndex(TMqMessageLog message) throws Exception {

        try {
            rabbitTemplate.convertAndSend("indexMsg", message);
            //如果confirm返回成功 则进行更新
            mqMessageLogService.changeMessageStatus(message.getMessageId(),
                    MSGStatusEnum.SEND_SUCCESS.getCode());
        } catch (AmqpException e) {
            //失败则进行具体的后续操作:重试 或者补偿等手段
            log.error("消息发送失败，需要进行异常处理...");
            mqMessageLogService.updataNextRetryTimeForNow(message.getMessageId());
        }

    }


}
