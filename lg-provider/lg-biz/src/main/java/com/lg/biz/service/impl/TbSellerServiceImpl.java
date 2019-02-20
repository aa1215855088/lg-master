package com.lg.biz.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.biz.mapper.TbSellerMapper;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbSellerServiceImpl extends ServiceImpl<TbSellerMapper, TbSeller> implements TbSellerService {
	
}
