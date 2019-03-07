package com.lg.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.validators.Insert;
import com.lg.product.mapper.TbSpecificationOptionMapper;
import com.lg.product.model.domain.TbSpecificationOption;
import com.lg.product.service.TbSpecificationOptionService;
import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
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
public class TbSpecificationOptionServiceImpl extends ServiceImpl<TbSpecificationOptionMapper, TbSpecificationOption> implements TbSpecificationOptionService {
    @Autowired
    private Validator validator;

    @Override
    public void deleteSpecificationOptionBySpecId(Long[] id) {
        checkNotNull(id);
        for (Long aLong : id) {
            this.baseMapper.delete(new QueryWrapper<TbSpecificationOption>().eq("spec_id", aLong));
        }
    }

    @Override
    public void addSpecificationOption(TbSpecificationOption tbSpecificationOption) {
        BeanValidators.validateWithException(validator, tbSpecificationOption, Insert.class);
        Integer index = this.baseMapper.insert(tbSpecificationOption);
        if (index != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "添加规格信息失败");
        }
    }

    @Override
    public List<TbSpecificationOption> findOptionBySpecId(Long specId) {
        return this.baseMapper.selectList(new QueryWrapper<TbSpecificationOption>().eq("spec_id", specId));
    }

    @Override
    public void deleteOneOptionBySpecId(Long specHiddenId) {
        if(StringUtils.isEmpty(specHiddenId.toString())){
            throw new BusinessException(ErrorCodeEnum.GL99990500, "删除规格选项失败,规格ID为空无法删除");
        }
        this.baseMapper.delete(new QueryWrapper<TbSpecificationOption>().eq("spec_id", specHiddenId));
    }
}
