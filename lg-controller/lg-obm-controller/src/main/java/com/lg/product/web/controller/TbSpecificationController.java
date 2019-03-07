package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbSpecification;
import com.lg.product.model.domain.TbSpecificationOption;
import com.lg.product.service.TbSpecificationOptionService;
import com.lg.product.service.TbSpecificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
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
@RequestMapping(value="/tbSpecification",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbSpecificationController", tags = "商品规格操作", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbSpecificationController extends BaseController {

    @Reference(version = "1.0.0")
    public TbSpecificationService tbSpecificationService;

    @Reference(version = "1.0.0")
    public TbSpecificationOptionService tbSpecificationOptionService;


    @GetMapping("/selectOptionList")
    @ApiOperation( httpMethod = "GET",value = "商品所有的商品规格选项列表")
    public List<Map> selectOption(){
        List<Map> list = this.tbSpecificationService.selectOptionList();
        return list;
    }


    //查所有
    @GetMapping("/findAllSpecification")
    @ApiOperation(httpMethod = "GET", value = "查询所有规格")
    public Wrapper findAllSpecification() {
        logger.info("查询所有规格");
        List<TbSpecification> tbSpecificationList = this.tbSpecificationService.findAllSpecification(new QueryWrapper<>());
        return WrapMapper.ok(tbSpecificationList);
    }

    //添加
    @GetMapping("/addSpecification/{pojoSpecificationName}/{optionNameAndOrderArray}")
    @ApiOperation(httpMethod = "GET", value = "删除规格")
    public Wrapper addSpecification(@ApiParam @PathVariable("pojoSpecificationName") String pojoSpecificationName, @PathVariable("optionNameAndOrderArray") String[] optionNameAndOrderArray) {
        logger.info("添加规格,specName={}", pojoSpecificationName);

        //首先判断表中是否含有该名称的规格，避免重复
        List<TbSpecification> tbSpecificationList = this.tbSpecificationService.findByName(pojoSpecificationName);
        if (!CollectionUtils.isEmpty(tbSpecificationList)) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加规格信息失败,该规格已存在");
        }

        //保存规格
        TbSpecification tbSpecification = new TbSpecification();
        tbSpecification.setSpecName(pojoSpecificationName);
        this.tbSpecificationService.addSpecification(tbSpecification);

        //再次查询插入的规格，得到规格id
        tbSpecificationList = this.tbSpecificationService.findByName(pojoSpecificationName);
        TbSpecification tbSpecification1 = tbSpecificationList.get(0);
        Long specId = tbSpecification1.getId();

        //循环，每一次使用split分割，得到specificationoption的'选项名称'和'排序'
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        String[] specOptionArray = null;
        for (String s : optionNameAndOrderArray) {
            specOptionArray = s.split("-");
            tbSpecificationOption.setOptionName(specOptionArray[0]);
            tbSpecificationOption.setOrders(Integer.parseInt(specOptionArray[1]));
            tbSpecificationOption.setSpecId(specId);
            this.tbSpecificationOptionService.addSpecificationOption(tbSpecificationOption);
        }

        return WrapMapper.ok();
    }

    //删除
    @GetMapping("/deleteById/{id}")
    @ApiOperation(httpMethod = "GET", value = "删除规格")
    public Wrapper deleteSpecification(@ApiParam @PathVariable("id") Long[] id) {
        logger.info("删除规格,ID={}", Arrays.toString(id));
        //删除规格表信息
        this.tbSpecificationService.deleteSpecification(id);

        logger.info("删除规格选项,specId={}", Arrays.toString(id));
        //删除规格选项
        this.tbSpecificationOptionService.deleteSpecificationOptionBySpecId(id);

        return WrapMapper.ok();
    }

    //根据规格id查询数据，回显
    @GetMapping("/initDataById/{id}")
    @ApiOperation(httpMethod = "GET", value = "根据规格ID查询规格选项")
    public Wrapper<List<TbSpecificationOption>> initDataById(@ApiParam @PathVariable Long id) {
        logger.info("根据规格ID查询规格选项,specId={}",id);
        List<TbSpecificationOption> tbSpecificationOptionList = this.tbSpecificationOptionService.findOptionBySpecId(id);
        return WrapMapper.ok(tbSpecificationOptionList);
    }

    //修改
    @GetMapping("/updateSpecification/{specHiddenId}/{pojoSpecificationName}/{optionNameAndOrderArray}")
    @ApiOperation(httpMethod = "GET", value = "修改规格")
    public Wrapper updateSpecification(@ApiParam @PathVariable("specHiddenId") Long specHiddenId, @PathVariable("pojoSpecificationName") String pojoSpecificationName, @PathVariable("optionNameAndOrderArray") String[] optionNameAndOrderArray) {
        logger.info("修改,id={}", specHiddenId);

        //修改规格
        TbSpecification tbSpecification = new TbSpecification();
        tbSpecification.setId(specHiddenId);
        tbSpecification.setSpecName(pojoSpecificationName);
        this.tbSpecificationService.updateSpecification(tbSpecification);

        //根据规格id删除该规格下全部的规格选项
        this.tbSpecificationOptionService.deleteOneOptionBySpecId(specHiddenId);

        //循环，每一次使用split分割，得到specificationoption的'选项名称'和'排序'
        TbSpecificationOption tbSpecificationOption = new TbSpecificationOption();
        String[] specOptionArray = null;
        for (String s : optionNameAndOrderArray) {
            specOptionArray = s.split("-");
            tbSpecificationOption.setOptionName(specOptionArray[0]);
            tbSpecificationOption.setOrders(Integer.parseInt(specOptionArray[1]));
            tbSpecificationOption.setSpecId(specHiddenId);
            this.tbSpecificationOptionService.addSpecificationOption(tbSpecificationOption);
        }

        return WrapMapper.ok();
    }

    //根据规格名称查询
    @GetMapping("/findSpecificationByName/{specName}")
    @ApiOperation(httpMethod = "GET", value = "根据规格名称查询规格")
    public Wrapper<List<TbSpecification>> findSpecificationByName(@ApiParam @PathVariable String specName) {
        logger.info("根据规格名称查询规格,NAME={}", specName);
        List<TbSpecification> tbSpecificationList = this.tbSpecificationService.findByName(specName);
        return WrapMapper.ok(tbSpecificationList);
    }
}
