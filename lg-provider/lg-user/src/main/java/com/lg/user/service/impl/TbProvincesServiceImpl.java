package com.lg.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.user.mapper.TbProvincesMapper;
import com.lg.user.model.domain.TbProvinces;
import com.lg.user.service.TbProvincesService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 省份信息表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Transactional
@Service(version = "1.0.0",timeout = 6000)
public class TbProvincesServiceImpl extends ServiceImpl<TbProvincesMapper, TbProvinces> implements TbProvincesService {
	
}
