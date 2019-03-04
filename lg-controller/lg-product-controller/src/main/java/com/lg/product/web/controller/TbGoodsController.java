package com.lg.product.web.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lg.commons.core.controller.BaseController;

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



    @Reference(version = "1.0.0")
    public TbGoodsService tbGoodsService;
    /*@PostMapping("/add")
    @ApiOperation(httpMethod = "POST",value = "商家添加商品")
    public Wrapper save(@RequestBody @ApiParam("商品基本信息") Goods goods){
        logger.info("添加商品基本信息，{}",goods);

        return this.tbGoodsService.save(goods);
    }*/




}
