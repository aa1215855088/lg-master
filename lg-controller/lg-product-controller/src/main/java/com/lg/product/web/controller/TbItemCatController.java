package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lg.commons.core.controller.BaseController;

import java.util.List;

/**
 * <p>
 * 商品类目 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/tbItemCat")
@Api(value = "WEB - ProductBrandController", tags = "商品分类API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbItemCatController extends BaseController {

    @Reference(version = "1.0.0")
    private TbItemCatService tbItemCatService;

    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取商品分类")
    public Wrapper<List<TbItemCat>> findAll(){

        return this.tbItemCatService.findAll();
    }
}
