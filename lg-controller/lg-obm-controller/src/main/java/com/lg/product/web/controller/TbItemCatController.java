package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lg.commons.core.controller.BaseController;

import java.util.Arrays;
import java.util.List;

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
    @GetMapping("/list")
    @ApiOperation(httpMethod = "GET",value = "获取商品类列表")
    public Wrapper<List<TbItemCat>> list(){
        logger.info("获取商品类列表");
        List<TbItemCat>  list=this.tbItemCatService.selectList(new QueryWrapper<>());
        return WrapMapper.ok(list);

    }

    @GetMapping("/findPage")
    @ApiImplicitParams({@ApiImplicitParam(value = "页数", paramType = "query"),@ApiImplicitParam(value = "行数", paramType = "query")})
    @ApiOperation(httpMethod = "GET",value = "获取商品类分页列表")
    public Wrapper<PageVO<TbItemCat>> findPage(Integer page, Integer rows){
        logger.info("品牌列表分页,pageNum={},pageSize={}", page, rows);

        return this.tbItemCatService.findByPage(page,rows);

    }

    @PostMapping("/save")
    @ApiOperation(httpMethod = "POST",value = "添加商品类")
    public Wrapper save(@RequestBody @ApiParam("商品类")TbItemCat tbItemCat){
        System.out.println(tbItemCat);
        logger.info("添加商品类");
        //this.tbItemCatService.save(tbItemCat);
        return this.tbItemCatService.save(tbItemCat);

    }

    @GetMapping("/findById/{id}")
    @ApiOperation(httpMethod = "GET",value = "根据ID查询商品类")
    public Wrapper findById(@ApiParam  @PathVariable Integer id){
        logger.info("根据ID查询商品类,ID={}",id);
        //this.tbItemCatService.findById(id);
        return this.tbItemCatService.findById(id);

    }

    @PutMapping("/update")
    @ApiOperation(httpMethod = "PUT",value = "修改商品类")
    public Wrapper update(@RequestBody @ApiParam("商品类")TbItemCat tbItemCat){
        logger.info("修改商品类,tbItemCat={}",tbItemCat);
        //this.tbItemCatService.updateTbItem(tbItemCat);
        return this.tbItemCatService.updateTbItem(tbItemCat);

    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(httpMethod = "DELETE",value = "删除商品类")
    public Wrapper delete(@ApiParam("商品类") @RequestBody @PathVariable("id") Long[] ids){
        logger.info("修改商品类,tbItemCat={}", Arrays.toString(ids));
        //this.tbItemCatService.delTbItem(ids);
        return this.tbItemCatService.delTbItem(ids);

    }

    @GetMapping("/findBySonId/{id}")
    @ApiOperation(httpMethod = "GET",value = "查询子商品类")
    public Wrapper findBySonId(@ApiParam("子商品类") @RequestBody @PathVariable("id") Long[] ids){
        logger.info("修改商品类,tbItemCat={}", ids);
        //this.tbItemCatService.delTbItem(ids);
        return this.tbItemCatService.findBySonId(ids);

    }


    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取商品分类")
    public Wrapper<List<TbItemCat>> findAll(){

        return this.tbItemCatService.findAll();
    }
}
