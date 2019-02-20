package com.lg.order.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.order.mapper.TbFreightTemplateMapper;
import com.lg.order.model.domain.TbFreightTemplate;
import com.lg.order.service.TbFreightTemplateService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000)
public class TbFreightTemplateServiceImpl extends ServiceImpl<TbFreightTemplateMapper, TbFreightTemplate> implements TbFreightTemplateService {
	
}
