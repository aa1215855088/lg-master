package com.lg.task.linstener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.product.model.domain.TMqMessageFailed;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.product.model.domain.TbSeller;
import com.lg.product.model.dto.ExceptionMsg;
import com.lg.task.mapper.TMqMessageFailedMapper;
import com.lg.task.service.ExceptionMsgService;
import com.lg.task.service.IMailService;
import com.lg.task.service.MessageService;
import com.lg.task.service.PageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
 * @description: 任务监听器 处理器
 * @author: 徐子楼
 * @create: 2019-02-22 17:25
 **/
@Component
@Slf4j
public class MessageListener {

    @Autowired
    private ExceptionMsgService exceptionMsgService;
    @Autowired
    private IMailService iMailService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PageService pageService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private TMqMessageFailedMapper tMqMessageFailedMapper;

    /**
     * 获取到异常消息储存并通知负责人处理
     *
     * @param exceptionMsgJson
     */
    @RabbitListener(queues = "exceptionMsg")
    @RabbitHandler
    public void exceptionMsgListener(String exceptionMsgJson) {
        log.info("-----------------RabbitOrderReceiver---------------------");
        log.info("消费端 exceptionMsg:{}", exceptionMsgJson);
        ExceptionMsg exceptionMsg = JSON.parseObject(exceptionMsgJson, ExceptionMsg.class);
        try {
            exceptionMsgService.addMsg(exceptionMsg);
            iMailService.sendSimpleMail(exceptionMsg.getEmail(), "徐子楼", exceptionMsgJson);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("exceptionMsg消费失败");
        }
        log.info("exceptionMsg消费成功:{}", exceptionMsgJson);
    }

    /**
     * 商品详情页面静态化
     *
     * @param messageLog
     */
    @RabbitListener(queues = "pageMsg")
    @RabbitHandler
    public void pageMsgListener(@Payload TMqMessageLog messageLog,
                                 Channel channel,
                                 @Headers Map<String, Object> headers) throws Exception {
        log.info("-----------------RabbitOrderReceiver---------------------");
        log.info("消费端 pageMsg:{}", messageLog);
        //判断是否重复消费
        if (messageService.isConsumer(messageLog.getMessageId())) {
            log.error("pageMsg:{}  重复消费", messageLog.getMessageId());
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
            return;
        }
        messageLog.setStatus(MSGStatusEnum.PROCESSING_IN.getCode());
        int row = messageService.updateByPrimaryKeySelective(messageLog);
        if (row != 0) {
            JSONArray objects = JSONArray.parseArray(messageLog.getMessage());
            if (this.pageService.createItemPageHtml(objects)) {
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                //手动应答
                channel.basicAck(deliveryTag, false);
                messageLog.setStatus(MSGStatusEnum.PROCESSING_SUCCESS.getCode());
                messageService.updateByPrimaryKeySelective(messageLog);
                log.info("pageMsg:{}  消费成功", messageLog.getMessageId());
            } else {
                if (!stringRedisTemplate.hasKey("PAGEMSG:" + messageLog.getMessageId())) {
                    stringRedisTemplate.opsForValue().set("PAGEMSG:" + messageLog.getMessageId(), "1", 1,
                            TimeUnit.DAYS);
                }
                String num = stringRedisTemplate.opsForValue().get("PAGEMSG:" + messageLog.getMessageId());
                log.error("pageMsg:{}  消费失败,重试第{}次", messageLog.getMessageId(), num);
                if (num.equals("3")) {
                    Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                    channel.basicAck(deliveryTag, false);
                    messageLog.setStatus(MSGStatusEnum.PROCESSING_FAILED.getCode());
                    messageService.updateByPrimaryKeySelective(messageLog);
                    TMqMessageFailed failed = new TMqMessageFailed(messageLog.getMessageId(), "消息处理失败",
                            "已达到最大重试次数，但是还是处理失败");
                    tMqMessageFailedMapper.insert(failed);
                }
                stringRedisTemplate.opsForValue().increment("PAGEMSG:" + messageLog.getMessageId(), 1);
                Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
                // 将消息重新放入队列
                channel.basicNack(deliveryTag, false, true);
            }
        }

    }

    @RabbitListener(queues = "sellerAuditMsg")
    @RabbitHandler
    public void sellerAuditMsgListener(String sellerAuditMsgJson) {
        log.info("-----------------RabbitOrderReceiver---------------------");
        log.info("消费端 sellerAuditMsg:{}", sellerAuditMsgJson);
        TbSeller tbSeller = JSON.parseObject(sellerAuditMsgJson, TbSeller.class);
        try {
            if ("1".equals(tbSeller.getStatus())) {
                iMailService.sendSimpleMail(tbSeller.getLinkmanEmail(), "乐购", "[" + tbSeller.getName() + "]审核通过");
            } else {
                iMailService.sendSimpleMail(tbSeller.getLinkmanEmail(), "乐购", "[" + tbSeller.getName() + "]审核不通过");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("sellerAuditMsg消费失败");
        }
        log.info("sellerAuditMsg消费成功:{}", sellerAuditMsgJson);
    }

}
