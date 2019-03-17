package com.lg.seckill.task;

import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.service.TbSeckillGoodsService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
 * @create: 2019-03-12 21:07
 **/
@Configuration
@EnableScheduling
@Slf4j
public class SeckillTask {

    @Autowired
    private TbSeckillGoodsService tbSeckillGoodsService;
    @Autowired
    private freemarker.template.Configuration configuration;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 每天凌晨3点执行一次
     */
    @Scheduled(cron = "0 0 3 * * ?")
    public void start() throws Exception {
        log.info("-----------------------初始化秒杀商品详情---------------------");
        String path = "E:\\javaweb\\项目实战\\lg-master\\lg-ui\\seckill\\";
        Template template = configuration.getTemplate("seckill-item.ftl");
        List<TbSeckillGoods> byTodaySeckill = this.tbSeckillGoodsService.findByTodaySeckill();
        //清空redis中的秒杀商品
        this.redisTemplate.delete("seckillHash");

        for (TbSeckillGoods tbSeckillGoods : byTodaySeckill) {
            this.redisTemplate.boundHashOps("seckillHash").put(tbSeckillGoods.getId(), tbSeckillGoods);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("seckill", tbSeckillGoods);
            Writer out = new FileWriter(path + tbSeckillGoods.getId() + ".html");
            //秒杀商品页面静态化
            template.process(dataModel, out);
            out.close();
        }
    }
}
