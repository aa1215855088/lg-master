package com.lg.product.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbBrandMapper;
import com.lg.product.model.domain.TbBrand;
import com.lg.product.service.TbBrandService;
import org.springframework.transaction.annotation.Transactional;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbBrandServiceImpl extends ServiceImpl<TbBrandMapper, TbBrand> implements TbBrandService {


    @Override
    public Wrapper save(TbBrand brand) throws Exception {
        if (true) {
            throw new Exception("错了");
        }
        this.baseMapper.insert(brand);
        return WrapMapper.ok();
    }
}
