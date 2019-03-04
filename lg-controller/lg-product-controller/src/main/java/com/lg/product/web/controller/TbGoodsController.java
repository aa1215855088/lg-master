package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.service.TbGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lg.commons.core.controller.BaseController;

import java.util.ArrayList;
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
@RequestMapping("/tbGoods")
@Api(value = "WEB - ProductBrandController", tags = "商品审核API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbGoodsController extends BaseController {

    @Reference(version = "1.0.0")
    private TbGoodsService tbGoodsService;
    /*
        显示全部
    */
    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取商品审核列表")
    public Wrapper<List<TbGoods>> findAll() {
        logger.info("查询所有的审核信息");
        return this.tbGoodsService.findAll();
    }

    /*
    分页
     */
    @GetMapping("/findPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "page", paramType = "query"),
            @ApiImplicitParam(value = "rows", paramType = "query")
    })
    @ApiOperation(httpMethod = "GET", value = "商品审核列表分页")
    public Wrapper<PageVO<TbGoods>> findPage(Integer page, Integer rows){

        logger.info("商品审核列表分页");
        return this.tbGoodsService.findPage(page,rows);
    }


    /*
    商品审核
     */
    @GetMapping("/updateStatus")
    @ApiOperation(httpMethod = "GET", value = "商品审核")
    public Wrapper updateStatus(Long [] ids,String status){
        logger.info("商品审核列表通过、驳回");
        return this.tbGoodsService.updateStatus(ids,status);
    }

    /*
    删除商品
     */
    @GetMapping("/deleteGoods")
    @ApiOperation(httpMethod = "GET", value = "删除商品")
    public Wrapper deleteGoods(Long [] ids){
        logger.info("删除商品");
        return this.tbGoodsService.deleteGoods(ids);
    }

    /*
    条件查询
     */
    @GetMapping("/findPageAndName")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "page", paramType = "query"),
            @ApiImplicitParam(value = "rows", paramType = "query")
    })
    @ApiOperation(httpMethod = "GET", value = "商品审核列表条件查询+分页")
    public Wrapper findPageAndName(String name,Integer page, Integer rows){
        logger.info("商品审核列表条件查询+分页");
        return this.tbGoodsService.findPageAndName(name,page,rows);
    }

}
