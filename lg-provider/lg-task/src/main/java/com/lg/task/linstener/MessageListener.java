package com.lg.task.linstener;

import com.alibaba.fastjson.JSON;
import com.lg.product.model.dto.ExceptionMsg;
import com.lg.task.service.ExceptionMsgService;
import com.lg.task.service.IMailService;
import com.sun.deploy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
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

    /**
     * 获取到异常消息储存并通知负责人处理
     *
     * @param exceptionMsgJson
     */
    @RabbitListener(queues = "exceptionMsg")
    public void exceptionMsgLinstener(String exceptionMsgJson) {
        ExceptionMsg exceptionMsg = JSON.parseObject(exceptionMsgJson, ExceptionMsg.class);
        exceptionMsgService.addMsg(exceptionMsg);
        iMailService.sendSimpleMail(exceptionMsg.getEmail(), "徐子楼", exceptionMsgJson);
        log.info("接受成功:{}", exceptionMsgJson);
    }

}
