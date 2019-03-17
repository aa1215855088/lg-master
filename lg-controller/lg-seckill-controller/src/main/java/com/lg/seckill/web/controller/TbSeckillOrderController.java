package com.lg.seckill.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.seckill.service.TbSeckillGoodsService;
import com.lg.seckill.service.TbSeckillOrderService;
import com.lg.seckill.web.limit.AccessLimitService;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/seckillOrder")
public class TbSeckillOrderController extends BaseController {

    @Resource
    private AccessLimitService accessLimitService;
    @Reference(version = "1.0.0")
    private TbSeckillOrderService orderService;
    @Reference(version = "1.0.0")
    private TbSeckillGoodsService seckillGoodsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/{id}")
    public Wrapper createSeckillOrder(@PathVariable Long id) throws InterruptedException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //限流
        if (accessLimitService.tryAcquire()) {
            logger.info("开始秒杀");
            return this.seckillGoodsService.startSeckill(id, authentication.getName());
        } else {
            logger.info("秒杀失败");
            return WrapMapper.error("活动太火爆,已经售罄啦！");
        }
    }
}
