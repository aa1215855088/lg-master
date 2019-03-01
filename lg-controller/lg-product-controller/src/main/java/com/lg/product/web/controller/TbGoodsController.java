package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.model.vo.GoodDto;
import com.lg.product.service.TbGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
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
@RequestMapping(value = "/tbGoods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbGoodsController", tags = "商品录入Api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbGoodsController extends BaseController {
    @Reference(version = "1.0.0")
    public TbGoodsService tbGoodsService;

    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", value = "商家添加商品")
    public Wrapper save(@RequestBody @ApiParam("商品基本信息") GoodDto goods) {
        logger.info("添加商品基本信息，{}", goods);
        Wrapper wrapper = this.tbGoodsService.save(goods);
        return wrapper;
    }
}
