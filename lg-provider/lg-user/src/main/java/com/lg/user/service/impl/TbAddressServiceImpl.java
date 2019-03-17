package com.lg.user.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.user.mapper.TbAddressMapper;
import com.lg.user.model.domain.TbAddress;
import com.lg.user.service.TbAddressService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Transactional
@Service(version = "1.0.0", timeout = 6000)
public class TbAddressServiceImpl extends ServiceImpl<TbAddressMapper, TbAddress> implements TbAddressService {


    @Override
    public List<TbAddress> getUserAddress(String name) {
        return this.baseMapper.selectList(new QueryWrapper<TbAddress>().eq("user_id", name));
    }
}
