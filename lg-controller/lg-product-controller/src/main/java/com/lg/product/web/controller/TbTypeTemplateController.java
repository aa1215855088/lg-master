package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.service.TbTypeTemplateService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.lg.commons.core.controller.BaseController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value="/tbTypeTemplate",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbTypeTemplateController", tags = "商品规格操作", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbTypeTemplateController extends BaseController {

    @Reference(version = "1.0.0")
    public TbTypeTemplateService typeTemplateService;

    @GetMapping("/findOne")
    @ApiOperation( httpMethod = "GET",value = "查询商品品牌信息")
    @ApiImplicitParams({@ApiImplicitParam(value = "商品品牌ID",paramType = "query")})
    public Wrapper<TbTypeTemplate>findOne(Long id){
        logger.info("查询商品品牌ID,id={}",id);
        Wrapper<TbTypeTemplate> tTbTypeTemplate= this.typeTemplateService.findOne(id);
        return tTbTypeTemplate;

    }


    @GetMapping("/findSpecList")
    @ApiOperation( httpMethod = "GET",value = "查询商品规格选项列表")
    @ApiImplicitParams({@ApiImplicitParam(value = "商品规格ID",paramType = "query")})
    public  Wrapper<List<Map>> findSpecList(Long id){
        logger.info("查询商品规格选项ID,id={}",id);
        return this.typeTemplateService.findSpecList(id);

    }

    @GetMapping("/findByName")
    @ApiOperation( httpMethod = "GET",value = "查询商品类型规格通过名称")
    @ApiImplicitParams({@ApiImplicitParam(value = "商品品牌Name",paramType = "query")})
    public Wrapper<PageVO<TbTypeTemplate>>findByName(Integer page, Integer rows, @ApiParam(name ="searchEntity",value = "条件") String  name){

        logger.info("查询商品品牌Name,tbTypeTemplate={}",name);
        Wrapper<PageVO<TbTypeTemplate>> tTbTypeTemplate= this.typeTemplateService.findByName(name,page,rows);
        return tTbTypeTemplate;
    }



    @GetMapping("/findAllList")
    @ApiOperation( httpMethod = "GET",value = "商品所有的商品规格选项列表")
    public  Wrapper<List<TbTypeTemplate>> list(){
        logger.info("商品所有的商品规格选项列表");
        List<TbTypeTemplate> list = this.typeTemplateService.selectList(new QueryWrapper<TbTypeTemplate>());
        return WrapMapper.ok(list);
    }


    @GetMapping("/findPage")
    @ApiImplicitParams({@ApiImplicitParam(value = "页数", paramType = "query"),@ApiImplicitParam(value = "行数", paramType = "query")})
    @ApiOperation(httpMethod = "GET",value = "获取商品规格选项")
    public Wrapper<PageVO<TbTypeTemplate>> findPage(Integer page, Integer rows){
        logger.info("品牌列表分页,pageNum={},pageSize={}", page, rows);
        return this.typeTemplateService.findByPage(page,rows);

    }



    @PostMapping("/Insert")
    @ApiOperation(httpMethod = "POST",value = "添加商品规格选项")
    public Wrapper save(@RequestBody @ApiParam("商品规格")TbTypeTemplate tbTypeTemplate) {
        System.out.println(tbTypeTemplate);
        logger.info("添加商品规格选项");
        //this.tbItemCatService.save(tbItemCat);
        return this.typeTemplateService.InsertTypeTemplate(tbTypeTemplate);
    }


    @PostMapping("/update")
    @ApiOperation(httpMethod = "PUT",value = "修改商品规格选项")
    public Wrapper update(@RequestBody @ApiParam("商品类品")TbTypeTemplate tbTypeTemplate){
        logger.info("增加商品类别模块,tbTypeTemplate={}",tbTypeTemplate);
        return this.typeTemplateService.updateTypeTemplate(tbTypeTemplate);

    }

    @PostMapping("/delete/{id}")
    @ApiOperation(httpMethod = "DELETE",value = "删除商品类商品规格选项")
    public Wrapper delete(@ApiParam("商品类品") @RequestBody @PathVariable("id") Long[] ids){
        logger.info("修改商品类,tbItemCat={}", Arrays.toString(ids));
        return this.typeTemplateService.deleteByIds(ids);

    }


}
