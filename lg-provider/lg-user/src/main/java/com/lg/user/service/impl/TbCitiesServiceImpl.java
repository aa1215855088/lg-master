package com.lg.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.user.mapper.TbCitiesMapper;
import com.lg.user.model.domain.TbCities;
import com.lg.user.service.TbCitiesService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 行政区域地州市信息表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Transactional
@Service(version = "1.0.0",timeout = 6000)
public class TbCitiesServiceImpl extends ServiceImpl<TbCitiesMapper, TbCities> implements TbCitiesService {
	
}
