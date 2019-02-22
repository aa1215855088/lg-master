package com.lg.content.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.content.mapper.TbContentMapper;
import com.lg.content.model.domain.TbContent;
import com.lg.content.service.TbContentService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0",timeout = 6000)
public class TbContentServiceImpl extends ServiceImpl<TbContentMapper, TbContent> implements TbContentService {
	
}
