package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.commons.util.validators.Update;
import com.lg.product.mapper.TbSpecificationMapper;
import com.lg.product.model.domain.TbSpecification;
import com.lg.product.service.TbSpecificationService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
public class TbSpecificationServiceImpl extends ServiceImpl<TbSpecificationMapper, TbSpecification> implements TbSpecificationService {

    @Override
    public List<Map> selectOptionList() {
        return baseMapper.selectOptionList();
    }

    @Autowired
    private Validator validator;

    @Override
    public List<TbSpecification> findAllSpecification(QueryWrapper<TbSpecification> queryWrapper) {
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public void addSpecification(TbSpecification tbSpecification) {
        BeanValidators.validateWithException(validator, tbSpecification, Insert.class);
        Integer index = this.baseMapper.insert(tbSpecification);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加规格信息失败");
        }
    }

    @Override
    public void deleteSpecification(Long[] id) {
        checkNotNull(id);
        Integer index = this.baseMapper.deleteBatchIds(Arrays.asList(id));
        if (index != id.length) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除规格信息失败");
        }

    }

    @Override
    public void updateSpecification(TbSpecification tbSpecification) {
        BeanValidators.validateWithException(validator, tbSpecification, Update.class);
        Integer index = this.baseMapper.updateById(tbSpecification);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "更新规格信息失败");
        }
    }

    @Override
    public List<TbSpecification> findByName(String specName) {
        return this.baseMapper.selectList(
                new QueryWrapper<TbSpecification>().eq("spec_name", specName));
    }
}
