package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbItemCatMapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000)
public class TbItemCatServiceImpl extends ServiceImpl<TbItemCatMapper, TbItemCat> implements TbItemCatService {

    @Override
    public Wrapper<List<TbItemCat>> findAll() {
        return WrapMapper.ok(this.baseMapper.selectList(new QueryWrapper<TbItemCat>()));
    }
}
