package com.lg.task.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.lg.commons.base.exception.BusinessException;
import com.lg.task.config.Sms;
import com.lg.task.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.cookie.SM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
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
 * @create: 2019-03-05 10:33
 **/
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String sendVerificationCode(String phone) {
        Long verificationCode = RandomUtil.randomLong(100000, 999999);
        String url = String.format(Sms.TPL_SEND_VERIFICATION_CODE_URL, phone, Sms.TPL_SEND_VERIFICATION_CODE,
                Sms.TPL_CODE,
                String.valueOf(verificationCode), Sms.TPL_AND, Sms.TPL_M, 6,
                Sms.KEY);
        String key = phone + String.valueOf(verificationCode);
        redisTemplate.opsForValue().set("VERIFICATIONCODE:" + key, "666", 360000);
        log.info("手机号:{},获取注册验证码:{}", phone, String.valueOf(verificationCode));
        String result = HttpUtil.get(url);
        JSONObject data = JSONObject.parseObject(result);
        if (data != null && data.getString("result") == null) {
            throw new BusinessException(500, "系统繁忙请稍后重试!");
        }
        return String.valueOf(verificationCode);
    }

    @Override
    public Boolean checkVerificationCode(String phone, String code) {
        log.info("手机号:{},验证验证码:{}", phone, code);
        String key = phone + code;
        if (redisTemplate.hasKey("VERIFICATIONCODE:" + key)) {
            return true;
        }
        return false;
    }

}
