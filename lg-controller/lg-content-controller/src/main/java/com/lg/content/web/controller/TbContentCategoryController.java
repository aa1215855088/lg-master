package com.lg.content.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.mapping.Map;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.lg.commons.core.controller.BaseController;

import javax.annotation.Resource;
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
@RequestMapping(value = "/TbContentCategory", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ProductContentCategoryController", tags = "广告分类API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbContentCategoryController extends BaseController {

    @Resource
    private TbContentCategoryService tbContentCategoryService;

//
//    //显示
//    @GetMapping("/query")
//    @ApiOperation(httpMethod = "GET", value = "获取广告分类列表")
//    public Wrapper<List<TbContentCategory>> list(){
//        logger.info("获取广告分类列表");
//        List<TbContentCategory> tbContentCategories = this.tbContentCategoryService.selectList(new QueryWrapper<>());
//        return WrapMapper.ok(tbContentCategories);
//    }
//
//    //分页
//    public PageVO<TbContentCategory> findByPage(Integer pageNum, Integer pageSize, TbContentCategory contentCategory) {
//        return null;
//    }
//
//    //查询
////    @GetMapping("/{id}")
////    @ApiOperation(httpMethod = "GET", value = "根据分类ID查询广告分类")
////    public Wrapper<TbContentCategory> findById(@ApiParam @PathVariable Integer id) {
////        logger.info("根据分类ID查询广告分类，ID={}", id);
////        TbContentCategory tbContentCategory = this.tbContentCategoryService.findById(id);
////        return  null;
////    }
//
//    //新建
//    @PostMapping("/")
//    @ApiOperation(httpMethod = "POST", value = "添加广告分类信息")
//    public Wrapper saveContentCategory(@RequestBody @ApiParam("广告分类") TbContentCategory contentCategory) {
//        logger.info("添加广告分类信息", contentCategory);
//        this.tbContentCategoryService.saveContentCategory(contentCategory);
//        return WrapMapper.ok(contentCategory);
//    }
//
//    //修改
//    @PutMapping("/")
//    @ApiOperation(httpMethod = "PUT", value = "更新广告分类信息")
//    public Wrapper updateContentCategory(@ApiParam("修改参数") @RequestBody TbContentCategory contentCategory) {
//        logger.info("更新广告分类信息，contentCategory={}", contentCategory);
//        this.tbContentCategoryService.updateContentCategory(contentCategory);
//        return WrapMapper.ok(contentCategory);
//    }
//
//    //删除
//    @DeleteMapping("/{id}")
//    @ApiOperation(httpMethod = "DELETE", value = "删除广告分类信息")
//    public Wrapper deleteContentCategory(Long[] ids) {
//        logger.info("删除广告分类信息，ID={}", Arrays.toString(ids));
//        this.tbContentCategoryService.deleteContentCategory(ids);
//        return  WrapMapper.ok(ids);
//    }
//
//
//    @GetMapping("/optionList")
//    @ApiOperation(httpMethod = "GET", value = "")
//    public Wrapper<List<Map>> findOptionList() {
//        logger.info("");
//        this.tbContentCategoryService.findOptionList();
//        return WrapMapper.ok();
//    }

}
