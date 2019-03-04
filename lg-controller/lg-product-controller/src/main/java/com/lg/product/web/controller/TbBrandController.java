package com.lg.product.web.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbBrand;
import com.lg.product.service.TbBrandService;
import com.lg.product.service.TbItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/tbBrand")
public class TbBrandController extends BaseController {

    @Reference
    private TbBrandService tbBrandService;

    @GetMapping("")
    public Wrapper<List<TbBrand>> hello() {

        return WrapMapper.ok(tbBrandService.selectList(new QueryWrapper<>()));

    }
}
