package com.lg.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.service.TbTypeTemplateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

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
 * @create: 2019-03-12 19:08
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbTypeTemplateServiceImplTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbTypeTemplateService tbTypeTemplateService;

    @Test
    public void add(){
        List<TbTypeTemplate> tbTypeTemplates = this.tbTypeTemplateService.selectList(new QueryWrapper<>());
        for (TbTypeTemplate tbTypeTemplate : tbTypeTemplates) {
            redisTemplate.boundHashOps("brandList").put(tbTypeTemplate.getBrandIds(),tbTypeTemplate.getName());
            redisTemplate.boundHashOps("specList").put(tbTypeTemplate.getSpecIds(),tbTypeTemplate.getName());
        }
    }
    @Test
    public void get(){
        System.out.println(redisTemplate.boundHashOps("brandList").get("手机"));
    }
}
