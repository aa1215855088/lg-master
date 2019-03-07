package com.lg.product.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbSpecificationMapper;
import com.lg.product.model.domain.TbSpecification;
import com.lg.product.service.TbSpecificationService;
import com.alibaba.dubbo.config.annotation.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
public class TbSpecificationServiceImpl extends ServiceImpl<TbSpecificationMapper, TbSpecification> implements TbSpecificationService {

    @Override
    public List<Map> selectOptionList() {
        return baseMapper.selectOptionList();
    }
}
