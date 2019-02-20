package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbSpecificationMapper;
import com.lg.product.model.domain.TbSpecification;
import com.lg.product.service.TbSpecificationService;
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
public class TbSpecificationServiceImpl extends ServiceImpl<TbSpecificationMapper, TbSpecification> implements TbSpecificationService {
	
}
