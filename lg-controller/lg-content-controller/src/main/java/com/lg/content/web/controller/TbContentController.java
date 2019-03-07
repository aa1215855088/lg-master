package com.lg.content.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.model.domain.TbContent;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;
import com.lg.content.service.TbContentService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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
@RequestMapping(value = "/tbContent",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbCententConroller",tags = "广告API",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbContentController extends BaseController {

    @Reference(version = "1.0.0")
    private TbContentService tbContentService;

    @Reference(version = "1.0.0")
    private TbContentCategoryService tbContentCategoryService;



    @GetMapping("")
    @ApiOperation(httpMethod = "GET", value = "获取所有广告信息")
    public Wrapper<List<TbContent>> findAll() {
        logger.info("获取所有广告信息");
        return this.tbContentService.findAll();
    }

    @PostMapping("/addContent")
    @ApiOperation(httpMethod = "POST", value = "添加广告信息")
    public Wrapper save(@RequestBody @ApiParam("广告信息") TbContent tbContent) {
        logger.info("添加广告信息,{}", tbContent);
        return this.tbContentService.save(tbContent);
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation(httpMethod = "DELETE", value = "删除广告信息")
    public Wrapper delete(@ApiParam @PathVariable("ids") Long[] ids) {
        logger.info("删除广告信息,ID={}", Arrays.toString(ids));
        return this.tbContentService.delByIds(ids);
    }

    @PutMapping("/updateById")
    @ApiOperation(httpMethod = "PUT", value = "更新广告信息")
    public Wrapper<TbContent> update(@ApiParam("修改参数") @RequestBody TbContent tbContent) {
        logger.info("更新广告信息", tbContent);
        return this.tbContentService.updateTbCentent(tbContent);
    }

    @GetMapping("/findById/{id}")
    @ApiOperation(httpMethod = "GET", value = "根据广告的ID查询广告信息")
    @ApiImplicitParams({@ApiImplicitParam(value = "广告的ID",paramType = "query")})
    public Wrapper<TbContent> findById (@ApiParam @PathVariable("id")Long id) {
        logger.info("根据广告ID查询广告信息,ID={}",id);
        return this.tbContentService.findById(id);
    }

    @GetMapping("/findAllCategory")
    @ApiOperation(httpMethod = "GET", value = "获取所有广告类型的信息")
    public Wrapper<List<TbContentCategory>> findAllCategory(){
        logger.info("获取所有广告类型的信息");
        return this.tbContentCategoryService.findAll();
    }
}
