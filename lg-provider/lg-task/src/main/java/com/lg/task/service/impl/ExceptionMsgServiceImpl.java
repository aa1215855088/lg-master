package com.lg.task.service.impl;

import com.alibaba.fastjson.JSON;
import com.lg.product.model.dto.ExceptionMsg;
import com.lg.task.service.ExceptionMsgService;
import com.lg.task.service.IMailService;
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
 * @create: 2019-02-23 17:44
 **/
@Service
public class ExceptionMsgServiceImpl implements ExceptionMsgService {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void addMsg(ExceptionMsg exceptionMsg) {
        String sql = "insert into tb_exception_msg (create_Time, exception_method, param, error_message, email) " +
                "values (?,?,?,?,?);";
        jdbcTemplate.update(sql, exceptionMsg.getCreateTime(), exceptionMsg.getExceptionMethod(),
                exceptionMsg.getParam(), exceptionMsg.getErrorMessage(), exceptionMsg.getEmail());

    }
}
