package com.lg.product.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.product.mapper.TbSellerMapper;
import com.lg.product.model.domain.TbSeller;
import com.lg.product.service.TbSellerService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbSellerServiceImpl extends ServiceImpl<TbSellerMapper, TbSeller> implements TbSellerService {
    public void add() {
        System.out.println("hello!");
    }

    @Override
    public TbSeller findByLoginName(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<TbSeller>()
                .eq(StrUtil.isNotBlank(username), "seller_id", username));
    }
}
