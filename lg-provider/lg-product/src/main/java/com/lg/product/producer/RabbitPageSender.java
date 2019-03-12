package com.lg.product.producer;

import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.product.service.TMqMessageLogService;
import lombok.extern.slf4j.Slf4j;
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
public class RabbitPageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private TMqMessageLogService mqMessageLogService;

    //发送消息方法调用: 构建自定义对象消息
    public void sendPage(TMqMessageLog message) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        CorrelationData correlationData = new CorrelationData(message.getMessageId() + "");
        rabbitTemplate.convertAndSend("pageMsg", message, correlationData);
//        throw new BusinessException("--test--");
    }

    //回调函数: confirm确认
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            log.info("correlationData: {}", correlationData);
            String messageId = correlationData.getId();
            if (ack) {
                //如果confirm返回成功 则进行更新
                mqMessageLogService.changeMessageStatus(Long.parseLong(messageId),
                        MSGStatusEnum.SEND_SUCCESS.getCode());
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                log.error("消息发送失败，需要进行异常处理...");
                mqMessageLogService.updataNextRetryTimeForNow(Long.parseLong(messageId));
            }
        }
    };

    //回调函数: return返回， 这里是预防消息不可达的情况，比如 MQ 里面没有对应的 exchange、queue 等情况，
    //    如果消息真的不可达，那么就要根据你实际的业务去做对应处理，比如是直接落库，记录补偿，还是放到死信队列里面，之后再进行落库
    //这里脱开实际业务 场景，不大好描述
    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode,
                                    String replyText, String exchange, String routingKey) {

            log.info("return exchange: {}, routingKey: {}, replyCode: {}, replyText: {}",
                    exchange, routingKey, replyCode, replyText);
        }
    };
}
