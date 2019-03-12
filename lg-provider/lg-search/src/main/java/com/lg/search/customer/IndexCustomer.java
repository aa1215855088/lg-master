package com.lg.search.customer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.product.service.TMqMessageLogService;
import com.lg.product.service.TbItemService;
import com.lg.search.service.IndexService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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
 * @create: 2019-03-12 15:32
 **/
@Component
@Slf4j
public class IndexCustomer {


    @Autowired
    private IndexService indexService;

    @RabbitListener(queues = "indexMsg")
    @RabbitHandler
    public void indexMsgListener(@Payload TMqMessageLog messageLog,
                                 Channel channel,
                                 @Headers Map<String, Object> headers) {
        log.info("-----------------RabbitOrderReceiver---------------------");
        log.info("消费端 indexMsg:{}", messageLog);
        List<Long> goodsIds = JSONArray.parseArray(messageLog.getMessage(), Long.class);
        try {
            this.indexService.saveAll(goodsIds);
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
            log.info("indexMsg:{}  消费成功", messageLog.getMessageId());
        } catch (Exception e) {
            e.printStackTrace();
            log.info("indexMsg:{}  消费失败", messageLog.getMessageId());
        }

    }
}
