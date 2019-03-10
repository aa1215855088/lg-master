package com.lg.product.task;

import com.lg.commons.base.constant.Constants;
import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.commons.base.enums.TypeEnum;
import com.lg.product.model.domain.TMqMessageFailed;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.product.producer.RabbitPageSender;
import com.lg.product.service.TMqMessageFailedService;
import com.lg.product.service.TMqMessageLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

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
 * @create: 2019-03-08 14:42
 **/
@Configuration
@EnableScheduling
@Slf4j
public class SendMessageTask {

    @Autowired
    private TMqMessageLogService messageLogService;

    @Autowired
    private TMqMessageFailedService messageFailedService;


    @Autowired
    private RabbitPageSender rabbitPageSender;

    @Scheduled(initialDelay = 3000, fixedDelay = 10000)
    public void reSend() {
        log.info("---------------定时任务开始---------------");
        List<TMqMessageLog> msgs = messageLogService.getNotProcessingInByType(TypeEnum.CREATE_PAGE.getCode(), null,
                new int[]{MSGStatusEnum.SENDING.getCode()});
        msgs.forEach(msg -> {
            if (msg.getTryCount() >= Constants.MAX_TRY_COUNT) {
//        		如果重试次数大于最大重试次数就不再重试，记录失败
                msg.setStatus(MSGStatusEnum.SEND_FAILURE.getCode());
                msg.setUpdateTime(LocalDateTime.now().plusMinutes(Constants.TRY_TIMEOUT));
                messageLogService.updateByPrimaryKeySelective(msg);
                TMqMessageFailed failed = new TMqMessageFailed(msg.getMessageId(), "消息发送失败", "已达到最大重试次数，但是还是发送失败");
                messageFailedService.insert(failed);
            } else {
//				未达到最大重试次数，可以进行重发消息
//				先改一下消息记录，保存好再发送消息
                msg.setNextRetry(LocalDateTime.now().plusMinutes(Constants.TRY_TIMEOUT));
                int row = messageLogService.updateTryCount(msg);
                try {
                    rabbitPageSender.sendOrder(msg);
                } catch (Exception e) {
                    log.error("sendOrder mq msg error: ", e);
                    messageLogService.updataNextRetryTimeForNow(msg.getMessageId());
                }
            }
        });
    }

}
