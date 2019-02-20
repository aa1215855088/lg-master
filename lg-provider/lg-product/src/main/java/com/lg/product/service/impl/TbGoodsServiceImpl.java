package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbGoodsMapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.service.TbGoodsService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbGoodsServiceImpl extends ServiceImpl<TbGoodsMapper, TbGoods> implements TbGoodsService {
	
}
