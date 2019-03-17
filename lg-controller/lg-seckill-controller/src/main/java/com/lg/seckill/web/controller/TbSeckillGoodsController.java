package com.lg.seckill.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.service.TbSeckillGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/seckillGoods")
@Api(tags = "秒杀API")
public class TbSeckillGoodsController extends BaseController {

    @Reference(version = "1.0.0")
    private TbSeckillGoodsService tbSeckillGoodsService;


    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取所有秒杀商品")
    public Wrapper<List<TbSeckillGoods>> findAll() {
        logger.info("获取秒杀商品");
        return tbSeckillGoodsService.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(httpMethod = "GET", value = "查询商品的库存")
    public Wrapper getStock(@PathVariable long id) {
        logger.info("获取商品库存:{}",id);
        return this.tbSeckillGoodsService.getStockById(id);
    }
}
