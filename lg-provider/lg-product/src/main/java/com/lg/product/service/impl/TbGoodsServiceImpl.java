package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbGoodsMapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.vo.GoodDto;
import com.lg.product.service.TbGoodsDescService;
import com.lg.product.service.TbGoodsService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000)
@Transactional
public class TbGoodsServiceImpl extends ServiceImpl<TbGoodsMapper, TbGoods> implements TbGoodsService {
    @Autowired
    private TbGoodsDescService tbGoodsDescService;

    @Autowired
    private Validator validator;

    @Override
    public Wrapper save(GoodDto goods) {
        BeanValidators.validateWithException(validator,goods,Insert.class);
        goods.getGoods().setAuditStatus("0");//设置未审核
        this.baseMapper.insert(goods.getGoods());//插入商品的基本信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        this.tbGoodsDescService.insert(goods.getGoodsDesc());//插入商品扩展表顺序
        return WrapMapper.ok(goods);
    }

}
