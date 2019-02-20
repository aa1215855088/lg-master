package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.exception.BusinessException;
import com.lg.product.exceptions.ProductBizException;
import com.lg.product.mapper.TbItemMapper;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import com.alibaba.dubbo.config.annotation.Service;
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
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbItemServiceImpl extends ServiceImpl<TbItemMapper, TbItem> implements TbItemService {

    @Override
    public List<TbItem> findAll() {
        if (true) {
            throw new ProductBizException(123, "你抛出了异常");
        }
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public String hello() {
        return "hello";
    }
}
