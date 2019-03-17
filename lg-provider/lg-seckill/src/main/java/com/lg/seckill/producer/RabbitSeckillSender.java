package com.lg.seckill.producer;


import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.seckill.model.domain.TMqMessageLog;
import com.lg.seckill.service.TMqMessageLogService;
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
public class RabbitSeckillSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //发送消息方法调用: 构建自定义对象消息
    public void sendSeckill(OrderMsg orderMsg) throws Exception {
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);

        CorrelationData correlationData = new CorrelationData(orderMsg.getId() + "");
        rabbitTemplate.convertAndSend("seckillMsg", orderMsg, correlationData);
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
                log.info("消息发送成功...");
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                log.error("消息发送失败，需要进行异常处理...");
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
