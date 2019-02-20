package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbContentCategoryMapper;
import com.lg.product.model.domain.TbContentCategory;
import com.lg.product.service.TbContentCategoryService;
import com.alibaba.dubbo.config.annotation.Service;

/**
 * <p>
 * 内容分类 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbContentCategoryServiceImpl extends ServiceImpl<TbContentCategoryMapper, TbContentCategory> implements TbContentCategoryService {
	
}
