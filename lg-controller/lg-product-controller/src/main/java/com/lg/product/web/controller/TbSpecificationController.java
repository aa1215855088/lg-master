package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lg.product.service.TbSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lg.commons.core.controller.BaseController;

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
@RequestMapping(value="/tbSpecification",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbSpecificationController", tags = "商品规格操作", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbSpecificationController extends BaseController {


    @Reference(version = "1.0.0")
    private TbSpecificationService tbSpecificationService;


    @GetMapping("/selectOptionList")
    @ApiOperation( httpMethod = "GET",value = "商品所有的商品规格选项列表")
    public List<Map> selectOption(){
        List<Map> list = this.tbSpecificationService.selectOptionList();
        return list;
    }
}
