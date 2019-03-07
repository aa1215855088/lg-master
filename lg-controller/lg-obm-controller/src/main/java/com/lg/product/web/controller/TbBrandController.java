package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.base.vo.Result;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbBrand;
import com.lg.product.service.TbBrandService;
import com.lg.product.service.TbItemService;
import com.lg.product.vo.BrandVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping(value = "/tbBrand", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - ProductBrandController", tags = "商品品牌API", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbBrandController extends BaseController {

    @Reference(version = "1.0.0")
    private TbBrandService tbBrandService;

    /**
     * 查询全部
     *
     * @return
     */
    @GetMapping("list")
    @ApiOperation(httpMethod = "GET", value = "获取所有商品品牌列表")
    public Wrapper<List<TbBrand>> list() {
        logger.info("获取品牌列表");
        List<TbBrand> list = this.tbBrandService.selectList(new QueryWrapper<>());
        return WrapMapper.ok(list);
    }

    /**
     * 返回全部列表 分页查询
     *
     * @return
     */
    @GetMapping("/listPage")
    @ApiOperation(httpMethod = "GET", value = "分页显示商品品牌列表")
    public Wrapper<PageVO<TbBrand>> listPage(Integer page, Integer rows) {
        logger.info("分页查询 品牌列表");
        return tbBrandService.listPaVo(page, rows);
    }


    @PostMapping("add")
    @ApiOperation(httpMethod = "POST", value = "添加商品品牌列表")
    public Wrapper add(@RequestBody TbBrand brand) {
       logger.info("添加品牌列表");
        this.tbBrandService.save(brand);
        return WrapMapper.ok();
    }

    @DeleteMapping("/delete/{ids}")
    @ApiOperation(httpMethod = "DELETE", value = "删除商品品牌信息")
    public Wrapper delete(@ApiParam  @PathVariable("ids") Long[] ids) {
        logger.info("删除品牌列表");
        return this.tbBrandService.delByBrand(ids);

    }
    @GetMapping("/findOne/{id}")
    @ApiOperation(httpMethod = "GET", value = "查询实体")
    public Wrapper<TbBrand> findOne(@ApiParam  @PathVariable("id")  Integer id) {
        logger.info("分页查询 品牌列表");
        return this.tbBrandService.findById(id);
    }

    @PostMapping("update")
    @ApiOperation(httpMethod = "POST", value = "修改商品品牌信息")
    public Wrapper update(@RequestBody TbBrand brand) {
            logger.info("修改品牌列表信息");
           this.tbBrandService.updateTbBrand(brand);
           return WrapMapper.ok();
    }

    @PostMapping("/search")
    @ApiOperation(httpMethod = "POST", value = "查询商品品牌信息")
    public Wrapper<PageVO<TbBrand>> search(@RequestBody(required = false) @ApiParam(name = "searchEntity", value = "条件")
                                              BrandVo brand,Integer page,Integer rows){

        logger.info("分页 按条件 查询品牌列表");
        return this.tbBrandService.findPage(page,rows,brand);
    }

    @GetMapping("/selectOptionList")
    @ApiOperation( httpMethod = "GET",value = "所有的商品品牌选项列表")
    public List<Map> selectOption(){
        logger.info("所有的商品品牌选项列表");
        List<Map> list = this.tbBrandService.selectOptionList();
        System.out.println(list);
        return list;
    }
}
