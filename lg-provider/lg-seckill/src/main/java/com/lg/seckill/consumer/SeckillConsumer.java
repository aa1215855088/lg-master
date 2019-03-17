package com.lg.seckill.consumer;


import cn.hutool.core.util.StrUtil;
import com.lg.commons.util.id.SnowflakeIdWorker;
import com.lg.seckill.producer.OrderMsg;
import com.lg.seckill.service.TbSeckillGoodsService;
import com.lg.seckill.service.TbSeckillOrderService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
 * @description: 任务监听器 处理器
 * @author: 徐子楼
 * @create: 2019-02-22 17:25
 **/
@Component
@Slf4j
public class SeckillConsumer {

    @Autowired
    private TbSeckillOrderService seckillOrderService;
    @Autowired
    private TbSeckillGoodsService seckillGoodsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 扣减库存 下单
     *
     * @param msg
     */
    @RabbitListener(queues = "seckillMsg")
    @RabbitHandler
    public void seckillConsumer(@Payload OrderMsg msg,
                                Channel channel,
                                @Headers Map<String, Object> headers) throws IOException {
        log.info("消费端 seckillMsg :{}", msg);
        String p = this.stringRedisTemplate.opsForValue().get(msg.getId().toString());
        if ("1".equals(p)) {
            log.info("重复消费");
            //手动应答
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
            return;
        }
        Integer updateRow = this.seckillGoodsService.deductioOfInventory(msg.getSeckillId());
        Integer addRow = this.seckillOrderService.createOrder(msg.getSeckillId(), msg.getUsername(),msg.getId());
        if (addRow == 1 && updateRow == 1) {
            log.info("{}消费成功", msg.getId());
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicAck(deliveryTag, false);
            this.stringRedisTemplate.opsForValue().set(msg.getId().toString(), "1");
        } else {
            log.info("{}消费失败 重试", msg.getId());
            Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
            channel.basicNack(deliveryTag, false, true);
        }
    }
}
