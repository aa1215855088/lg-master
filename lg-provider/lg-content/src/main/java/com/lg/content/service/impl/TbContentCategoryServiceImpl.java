package com.lg.content.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.content.mapper.TbContentCategoryMapper;
import com.lg.content.model.domain.TbContentCategory;
import com.lg.content.service.TbContentCategoryService;

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
