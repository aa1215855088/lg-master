package com.lg.task.service.impl;

import com.lg.task.service.IMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

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
 * @create: 2019-02-23 17:46
 **/
@Service
public class IMailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {

    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content, String... cc) {

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {

    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc) {

    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);

        mailSender.send(message);
    }

    @Override
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId,
                                 String... cc) {

    }
}
