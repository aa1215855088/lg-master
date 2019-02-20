package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbBrand;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.lg.commons.core.controller.BaseController;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/tbItem")
public class TbItemController extends BaseController {

    @Reference(version = "1.0.0")
    private TbItemService itemService;

    @GetMapping("")
    public Wrapper<List<TbItem>> findAll() {
        logger.info("查询所有的商品");
        return WrapMapper.ok(this.itemService.findAll());
    }

    @GetMapping("/{id}")
    public Wrapper<TbItem> findById(@PathVariable Long id) {
        TbItem tbItem = this.itemService.selectById(id);
        return WrapMapper.ok(tbItem);
    }

    @GetMapping("/hello")
    public String hello() {
        return this.itemService.hello();
    }
}
