package com.lg.biz.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.service.TbTypeTemplateService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/tbTypeTemplate")
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



	
}
