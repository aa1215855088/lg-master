package com.lg.seckill.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.id.SnowflakeIdWorker;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.seckill.mapper.TbSeckillGoodsMapper;
import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.producer.OrderMsg;
import com.lg.seckill.producer.RabbitSeckillSender;
import com.lg.seckill.service.TbSeckillGoodsService;
import lombok.extern.slf4j.Slf4j;
import netscape.javascript.JSObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0")
@Transactional
@Slf4j
public class TbSeckillGoodsServiceImpl extends ServiceImpl<TbSeckillGoodsMapper, TbSeckillGoods> implements TbSeckillGoodsService {


    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private RabbitSeckillSender rabbitSeckillSender;

    @Override
    public Wrapper<List<TbSeckillGoods>> findAll() {
        List<TbSeckillGoods> tbSeckillGoods = null;
        Map<Long, TbSeckillGoods> seckillHash = this.redisTemplate.boundHashOps("seckillHash").entries();
        if (seckillHash == null || seckillHash.values().isEmpty()) {
            tbSeckillGoods = this.baseMapper.selectList(new QueryWrapper<>());
            tbSeckillGoods.forEach(seckillGoods -> {
                this.redisTemplate.boundHashOps("seckillHash").put(seckillGoods.getId(), seckillGoods);
            });
        } else {
            tbSeckillGoods = new ArrayList<>(seckillHash.values());
        }
        return WrapMapper.ok(tbSeckillGoods);
    }

    @Override
    public List<TbSeckillGoods> findByTodaySeckill() {
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public Wrapper getStockById(long id) {
        TbSeckillGoods seckillHash = (TbSeckillGoods) this.redisTemplate.boundHashOps("seckillHash").get(id);
        if (seckillHash == null) {
            seckillHash = this.baseMapper.selectOne(new QueryWrapper<TbSeckillGoods>().eq("id", id));
            if (seckillHash != null) {
                this.redisTemplate.boundHashOps("seckillHash").put(seckillHash.getId(), seckillHash);
            }
        }
        return WrapMapper.ok(seckillHash.getStockCount());
    }

    @Override
    public boolean checkStartSeckill(Long id) {
        TbSeckillGoods seckillGoods = (TbSeckillGoods) this.redisTemplate.boundHashOps("seckillHash").get(id);
        LocalDateTime startTime = seckillGoods.getStartTime();
        LocalDateTime endTime = seckillGoods.getEndTime();
        LocalDateTime nowTime = LocalDateTime.now();
        return nowTime.isAfter(startTime) && nowTime.isBefore(endTime);
    }

    public static void main(String[] args) {
        LocalDateTime endTime = LocalDateTime.of(2019, 3, 14, 12, 0);
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println(nowTime.isAfter(endTime));
    }

    @Override
    public Wrapper startSeckill(Long id, String name) {

        //1.判断秒杀活动是否开始或者结束
        if (!checkStartSeckill(id)) {
            return WrapMapper.wrap(600, "该秒杀商品不在活动时间内,请注意活动时间!");
        }
        log.info("开始获取锁资源...");
        String lockKey = "BM_MARKET_SECKILL_" + id;
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        String lockVal = String.valueOf(snowflakeIdWorker.nextId());
        try {
            if (!this.stringRedisTemplate.opsForValue().setIfAbsent(lockKey, lockVal)) {
                log.info("获取锁资源失败！快速返回");
                return WrapMapper.wrap(600, "很抱歉，人太多了，换个姿势再试试~~");
            }
            log.info("获取锁资源");
            //做用户重复购买检验
            if (stringRedisTemplate.hasKey("BM_MARKET_SECKILL_LIMIT_" + id + "_" + name)) {
                log.info("已经检测到用户重复购买...");
                return WrapMapper.wrap(600, "您正在参与该活动,不能重复购买!");
            } else {
                TbSeckillGoods seckillHash = (TbSeckillGoods) this.redisTemplate.boundHashOps("seckillHash").get(id);
                //获取库存
                int surplusStock = seckillHash.getStockCount() == null ? 0 : seckillHash.getStockCount();
                //扣库存
                surplusStock--;
                if (surplusStock >= 0) {
                    //redis库扣减库存
                    seckillHash.setStockCount(surplusStock);
                    redisTemplate.boundHashOps("seckillHash").put(id, seckillHash);
                    //维护一个key，防止用户在该活动重复购买，当支付过期之后应该维护删除该标志
                    stringRedisTemplate.opsForValue().set("BM_MARKET_SECKILL_LIMIT_" + id + "_" + name, "true",
                            3600 * 24 * 7, TimeUnit.SECONDS);
                    //异步 数据库扣减库存和插入订单
                    //创建分布式ID
                    long onlyId = snowflakeIdWorker.nextId();
                    this.stringRedisTemplate.opsForValue().set(String.valueOf(onlyId), "0");
                    OrderMsg orderMsg = new OrderMsg(onlyId, id, name);
                    this.rabbitSeckillSender.sendSeckill(orderMsg);
                    //success
                    return WrapMapper.ok(onlyId);
                } else {
                    log.info("商品库存不足");
                    return WrapMapper.wrap(600, "秒杀失败，商品已经售罄");
                }
            }
        } catch (Exception e) {
            log.info("秒杀失败");
            return WrapMapper.wrap(600, "秒杀失败，商品已经售罄");
        } finally {
            log.info("释放锁资源...");
            String currentValue = (String) stringRedisTemplate.opsForValue().get(lockKey);
            if (!StrUtil.isEmpty(currentValue) && currentValue.equals(lockVal)) {
                stringRedisTemplate.delete(lockKey);
            }
        }
    }

    @Override
    public Integer deductioOfInventory(Long seckillId) {
        TbSeckillGoods seckillGoods = this.baseMapper.selectById(seckillId);
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        return this.baseMapper.updateById(seckillGoods);
    }

}
