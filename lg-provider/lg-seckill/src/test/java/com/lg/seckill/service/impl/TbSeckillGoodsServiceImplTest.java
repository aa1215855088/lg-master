package com.lg.seckill.service.impl;


import com.lg.commons.util.wrapper.Wrapper;
import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.service.TbSeckillGoodsService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
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
 * @create: 2019-03-13 09:02
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class TbSeckillGoodsServiceImplTest {

    @Autowired
    private TbSeckillGoodsService tbSeckillGoodsService;
    @Autowired
    private Configuration configuration;
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void findAll() throws IOException, TemplateException {
        String path = "E:\\javaweb\\项目实战\\lg-master\\lg-ui\\seckill\\";
        Template template = configuration.getTemplate("seckill-item.ftl");
        List<TbSeckillGoods> byTodaySeckill = this.tbSeckillGoodsService.findByTodaySeckill();


        for (TbSeckillGoods tbSeckillGoods : byTodaySeckill) {
            this.redisTemplate.boundHashOps("seckillHash").put(tbSeckillGoods.getId(), tbSeckillGoods);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("seckill", tbSeckillGoods);
            Writer out = new FileWriter(path + tbSeckillGoods.getId() + ".html");
            template.process(dataModel, out);
            out.close();
        }
//        this.redisTemplate.boundListOps("seckillList").range(0, -1);
    }

    @Test
    public void get() {
        this.redisTemplate.delete("seckillHash");
    }

    @Test
    public void getAll(){
        Map<Long,TbSeckillGoods> seckillHash = this.redisTemplate.boundHashOps("seckillHash").entries();
        for (Long aLong : seckillHash.keySet()) {
            System.out.println(aLong);
            System.out.println(seckillHash.get(aLong));
        }
    }

}
