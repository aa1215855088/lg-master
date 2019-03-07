package com.lg.task.service.impl;


import com.lg.task.service.IMailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;

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
 * @create: 2019-02-23 17:51
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class IMailServiceImplTest {
    @Autowired
    private IMailService iMailService;
    @Autowired
    private TemplateEngine templateEngine;//注入模板引擎
    @Autowired
    private StringRedisTemplate template;

    @Test
    public void sendSimpleMail() throws MessagingException {
        String emailContent = templateEngine.process("msgTemplate", new Context());
        this.iMailService.sendHtmlMail("1013629501@qq.com", "徐子楼", emailContent);
    }

    @Test
    public void redisTest(){
        this.template.opsForValue().set("test","123",60000);
    }
}
