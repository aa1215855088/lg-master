package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.commons.base.vo.GoodsVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbGoodsDescService;
import com.lg.product.service.TbGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.lg.commons.core.controller.BaseController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value = "/tbGoods",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbGoodsController",tags = "商品录入Api",produces = MediaType.APPLICATION_JSON_UTF8_VALUE )
public class TbGoodsController extends BaseController {


    @Reference(version = "1.0.0")
    public TbGoodsService tbGoodsService;
    /*@PostMapping("/add")
    @ApiOperation(httpMethod = "POST",value = "商家添加商品")
    public Wrapper save(@RequestBody @ApiParam("商品基本信息") Goods goods){
        logger.info("添加商品基本信息，{}",goods);

        return this.tbGoodsService.save(goods);
    }*/




}
