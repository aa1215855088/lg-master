package com.lg.product.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.exceptions.ProductBizException;
import com.lg.product.mapper.TbItemMapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0")
@Transactional
public class TbItemServiceImpl extends ServiceImpl<TbItemMapper, TbItem> implements TbItemService {

    @Override
    public Wrapper<List<TbItem>> findAll() {
        if (true) {
            throw new ProductBizException(123, "你抛出了异常");
        }
        return WrapMapper.ok(this.baseMapper.selectList(new QueryWrapper<>()));
    }

    @Override
    public String hello() {
        return "hello";
    }
}
