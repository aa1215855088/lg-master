package com.lg.task.service.impl;

import com.lg.commons.base.constant.Constants;
import com.lg.commons.base.enums.MSGStatusEnum;
import com.lg.product.model.domain.TMqMessageLog;
import com.lg.task.mapper.TMqMessageLogMapper;
import com.lg.task.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
 * @create: 2019-03-09 15:35
 **/
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private TMqMessageLogMapper tMqMessageLogMapper;

    @Override
    public int
    updateByPrimaryKeySelective(TMqMessageLog messageLog) {
        String sql = "update t_mq_message_log set  update_time=now(),status=? where message_id=?";
        int update = this.jdbcTemplate.update(sql, messageLog.getStatus(), messageLog.getMessageId());
        return update;
    }

    @Override
    public boolean isConsumer(Long messageId) {
        return this.tMqMessageLogMapper.selectById(messageId).getStatus().equals(MSGStatusEnum.PROCESSING_SUCCESS);
    }
}
