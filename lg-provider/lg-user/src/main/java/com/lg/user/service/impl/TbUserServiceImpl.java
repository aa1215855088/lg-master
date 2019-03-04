package com.lg.user.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.exception.BusinessException;
import com.lg.user.mapper.TbUserMapper;
import com.lg.user.model.domain.TbUser;
import com.lg.user.service.TbUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements TbUserService {

    @Override
    public List<TbUser> findAll() {
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public TbUser findByLoginName(String username) {
        if (StrUtil.isBlank(username)) {
            throw new BusinessException("用户名为空");
        }
        return this.baseMapper.selectOne(new QueryWrapper<TbUser>().eq("username", username));
    }
}
