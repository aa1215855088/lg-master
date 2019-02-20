package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbItemCatMapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.service.TbItemCatService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * <p>
 * 商品类目 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbItemCatServiceImpl extends ServiceImpl<TbItemCatMapper, TbItemCat> implements TbItemCatService {
	
}
