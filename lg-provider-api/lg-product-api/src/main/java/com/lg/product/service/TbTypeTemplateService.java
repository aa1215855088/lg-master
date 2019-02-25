package com.lg.product.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.model.domain.TbTypeTemplate;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbTypeTemplateService extends IService<TbTypeTemplate> {
    Wrapper<TbTypeTemplate> findOne(Long id);
	
}
