package com.lg.biz.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping(value = "/tbItemCats",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbItemCatController",tags = "商品录入Api",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbItemCatController extends BaseController {


    @Reference(version = "1.0.0")
    public TbItemCatService tbItemCatService;

    @GetMapping("/findByParentId")
    @ApiOperation( httpMethod = "GET",value = "获取商品一级分类信息")
    @ApiImplicitParams({@ApiImplicitParam(value = "顶层ID",paramType = "query")})
    public Wrapper<List<TbItemCat>>findByParentId(Long parentId){
        logger.info("查询商品一级分类信息,parentId={}",parentId);
        Wrapper<List<TbItemCat>> tbItemCatServiceByParentId = this.tbItemCatService.findByParentId(parentId);
        return  tbItemCatServiceByParentId;
    }

    @GetMapping("/findOne")
    @ApiOperation( httpMethod = "GET",value = "获取模板类型ID")
    @ApiImplicitParams({@ApiImplicitParam(value = "三级分类ID",paramType = "query")})
    public  Wrapper<TbItemCat>findOne(Long id){
        logger.info("查询模板类型ID,id={}",id);
        Wrapper<TbItemCat> ItemCattypId = this.tbItemCatService.findOne(id);
        return ItemCattypId;
    }
	
}
