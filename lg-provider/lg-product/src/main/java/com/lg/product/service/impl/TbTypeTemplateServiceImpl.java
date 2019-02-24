package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.mapper.TbTypeTemplateMapper;
import com.lg.product.model.domain.TbItemCat;
import com.lg.product.model.domain.TbTypeTemplate;
import com.lg.product.service.TbTypeTemplateService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbTypeTemplateServiceImpl extends ServiceImpl<TbTypeTemplateMapper, TbTypeTemplate> implements TbTypeTemplateService {

    @Override
    public Wrapper<TbTypeTemplate> findOne(Long id) {
        checkNotNull(id);
        TbTypeTemplate tbTypeTemplate = this.baseMapper.selectOne(new QueryWrapper<TbTypeTemplate>().eq("id", id));
        if (tbTypeTemplate==null){
            throw  new BusinessException(ErrorCodeEnum.GL99990500,"没有查询到商品品牌信息");
        }
        return WrapMapper.ok(tbTypeTemplate);
    }
}
