package com.lg.content.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.lg.commons.core.controller.BaseController;

import java.util.Arrays;
import java.util.List;


/**
 * <p>
 * 内容分类 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value = "/contentCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ProductContentCategoryController", tags = "广告分类API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbContentCategoryController extends BaseController {

    @Reference(version = "1.0.0")
    private TbContentCategoryService tbContentCategoryService;


    //显示
    @GetMapping("/findAll")
    @ApiOperation(httpMethod = "GET", value = "获取广告分类列表")
    public Wrapper<List<TbContentCategory>> findAll(){
        logger.info("获取广告分类列表");
        return WrapMapper.ok(this.tbContentCategoryService.selectList(new QueryWrapper<>()));
    }


    //分页
    @GetMapping("/findPage")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", paramType = "query"),
            @ApiImplicitParam(value = "行数", paramType = "query")
        })
    @ApiOperation(httpMethod = "GET", value = "获取广告分类列表分页")
    public Wrapper<PageVO<TbContentCategory>> findPage(Integer page, Integer rows) {
        logger.info("广告分类列表分页,pageNum={},pageSize={}", page, rows);
        return tbContentCategoryService.findPage(page, rows);
    }


    //分页 模糊查询 名字
    @PostMapping("/search")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", paramType = "query"),
            @ApiImplicitParam(value = "行数", paramType = "query")
    })
    @ApiOperation(httpMethod = "POST", value = "获取查询的广告分类信息")
    public Wrapper<PageVO<TbContentCategory>> search(@RequestBody(required = false) @ApiParam(name = "searchEntity", value = "条件") TbContentCategory contentCategory, Integer page, Integer rows){
        logger.info("广告分类信息查询,pageNum={},pageSize={},contentCategory={}", page, rows, contentCategory);
        return tbContentCategoryService.findPage(page, rows, contentCategory);
    }


    //编号 查询
    @GetMapping("/findById/{id}")
    @ApiOperation(httpMethod = "GET", value = "根据ID查询广告分类")
    public Wrapper<TbContentCategory> findById(@ApiParam @PathVariable Integer id) {
        logger.info("根据分类ID查询广告分类，ID={}", id);
        return this.tbContentCategoryService.findById(id);
    }


    //新建
    @PostMapping("/save")
    @ApiOperation(httpMethod = "POST", value = "添加广告分类信息")
    public Wrapper save(@RequestBody @ApiParam("广告分类") TbContentCategory contentCategory) {
        logger.info("添加广告分类信息,{}", contentCategory);
        return WrapMapper.ok( this.tbContentCategoryService.save(contentCategory));
    }


    //修改
    @PutMapping("/update")
    @ApiOperation(httpMethod = "PUT", value = "更新广告分类信息")
    public Wrapper update(@RequestBody @ApiParam("修改参数") TbContentCategory contentCategory) {
        logger.info("更新广告分类信息，contentCategory={}", contentCategory);
        return WrapMapper.ok(this.tbContentCategoryService.update(contentCategory));
    }


    //删除
    @DeleteMapping("/delete/{id}")
    @ApiOperation(httpMethod = "DELETE", value = "删除广告分类信息")
    public Wrapper delete(@RequestBody @ApiParam @PathVariable("id") Long[] ids) {
        logger.info("删除广告分类信息,ID={}", Arrays.toString(ids));
        return  WrapMapper.ok(this.tbContentCategoryService.delete(ids));
    }



}
