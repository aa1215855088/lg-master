package com.lg.biz.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.biz.mapper.TbSellerMapper;
import com.lg.biz.model.domain.TbSeller;
import com.lg.biz.service.TbSellerService;
import com.lg.commons.base.enums.ErrorCodeEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.validators.BeanValidators;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.Validator;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
public class TbSellerServiceImpl extends ServiceImpl<TbSellerMapper, TbSeller> implements TbSellerService {
    @Autowired
    private Validator validator;

    @Override
    public TbSeller findByLoginName(String username) {
        return this.baseMapper.selectOne(new QueryWrapper<TbSeller>()
                .eq(StrUtil.isNotBlank(username), "seller_id", username));
    }

    @Override
    public Wrapper sellerInsert(TbSeller tbSeller) {
        BeanValidators.validateWithException(validator, tbSeller);
        /*Integer  num=this.baseMapper.insert(tbSeller);*/
        tbSeller.setStatus("0");
        Integer insert = this.baseMapper.insert(tbSeller);
        if (insert != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "商家注入失败");
        }
        return WrapMapper.ok(insert);
    }

    @Override
    public Wrapper<TbSeller> findById(String name) {
        if (StrUtil.isBlank(name)) {
            throw new BusinessException(500, "用户名为空");
        }
        TbSeller tbSeller = this.baseMapper.selectOne(new QueryWrapper<TbSeller>().eq("seller_id", name));
        return WrapMapper.ok(tbSeller);
    }

    @Override
    public Wrapper updateSellerInfo(TbSeller tbSeller) {
        /*BeanValidators.validateWithException(validator, tbSeller);*/
         Integer flag= this.baseMapper.updateById(tbSeller);
        if (flag != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "商家修改资料失败");
        }
        return WrapMapper.ok();
    }

    @Override
    public Wrapper updatePasswordById(TbSeller tbSeller) {
        Integer flag=this.baseMapper.updatePassword(tbSeller);
        if (flag != 1) {
            throw new BusinessException(ErrorCodeEnum.GL99990500, "密码修改失败");
        }
        return WrapMapper.ok();
    }

  /*  @Override
    public TbSeller selectPassword(String  password) {
       return this.baseMapper.selectOne(new QueryWrapper<TbSeller>()
                .eq("password",password));
    }*/
}
