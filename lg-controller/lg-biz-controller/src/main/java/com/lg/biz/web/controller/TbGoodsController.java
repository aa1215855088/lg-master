package com.lg.biz.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.base.vo.GoodsVo;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.dto.GoodDTD;
import com.lg.product.model.dto.Goods;
import com.lg.product.model.vo.GoodsVO;
import com.lg.product.service.TbGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
@RequestMapping(value = "/tbGoods", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "WEB - TbGoodsController", tags = "商品录入Api", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class TbGoodsController extends BaseController {
    @Reference(version = "1.0.0")
    public TbGoodsService tbGoodsService;

    @PostMapping("/add")
    @ApiOperation(httpMethod = "POST", value = "商家添加商品")
    public Wrapper save(@RequestBody @ApiParam("商品基本信息") GoodDTD goods, Principal principal) {
        goods.getGoods().setSellerId(principal.getName());
        logger.info("添加商品基本信息，{}", goods);
        Wrapper wrapper = this.tbGoodsService.save(goods);
        return wrapper;
    }

    @GetMapping(value = "findAll")
    @ApiOperation(httpMethod = "GET", value = "查询所有")
    public Wrapper<List<TbGoods>> findAll(Principal principal) {
        logger.info("商家查询");
        List<TbGoods> list = this.tbGoodsService.selectList(new QueryWrapper<TbGoods>().eq("seller_id",principal.getName()));
        return WrapMapper.ok(list);
    }

    @GetMapping(value = "/findPage")
    @ApiOperation(httpMethod = "GET", value = "分页查询")
    public Wrapper<PageVO<TbGoods>> findPage(Integer page, Integer rows) {
        logger.info("商家分页查询,{},{}", page, rows);
        return this.tbGoodsService.pageList(page, rows);
    }

    @GetMapping(value = "/delete/{id}")
    @ApiOperation(httpMethod = "GET", value = "商家删除商品")
    public Wrapper delete(@ApiParam @PathVariable("id") Long[] id) {
        logger.info("商家删除商品{}", id);
        return this.tbGoodsService.delete(id);
    }

    @GetMapping(value = "/findOne/{id}")
    @ApiOperation(httpMethod = "GET", value = "商家查询实体")
    public Wrapper<TbGoods> findOne(@RequestBody @ApiParam @PathVariable("id") Long id) {
        logger.info("商家查询实体{}", this.tbGoodsService.findOne(id));
        return this.tbGoodsService.findOne(id);
    }

    @PostMapping(value = "/update")
    @ApiOperation(httpMethod = "POST", value = "商家修改商品")
    public Wrapper update(@RequestBody TbGoods tbGoods) {
        logger.info("商家修改{}", tbGoods.getGoodsName());
        this.tbGoodsService.update(tbGoods);
        return WrapMapper.ok();
    }

    @PostMapping(value = "/search")
    @ApiOperation(httpMethod = "POST", value = "商品列表搜索")
    public Wrapper<PageVO<Goods>> search(@RequestBody(required = false) @ApiParam(name = "searchEntity", value = "条件")
                                                 GoodsVO goods, Integer page, Integer rows,Principal principal) {

        logger.info("商品列表搜索,{},{},{}", page, rows, goods);

        return this.tbGoodsService.search(page, rows, goods,principal.getName());
    }

    @PostMapping(value = "/shield/{id}")
    @ApiOperation(httpMethod = "POST", value = "商家屏蔽商品")
    public Wrapper shield(@ApiParam @PathVariable("id") Long[] id) {
        logger.info("屏蔽商品:{}", Arrays.toString(id));
        this.tbGoodsService.shield(id);
        return WrapMapper.ok();
    }

    @PostMapping(value = "/submitAudit/{id}")
    @ApiOperation(httpMethod = "POST", value = "商家屏蔽商品")
    public Wrapper submitAudit(@ApiParam @PathVariable("id") Long[] id) {
        logger.info("提交审核ID:{}", Arrays.toString(id));
        return this.tbGoodsService.submitAudit(id);
    }
}
