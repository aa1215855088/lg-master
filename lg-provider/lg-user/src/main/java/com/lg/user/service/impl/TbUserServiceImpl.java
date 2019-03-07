package com.lg.user.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.user.mapper.TbUserMapper;
import com.lg.user.model.domain.TbUser;
import com.lg.user.model.dto.UserDTO;
import com.lg.user.service.TbUserService;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 10000)
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

    @Override
    public Wrapper checkUsername(String username) {
        if (StrUtil.isBlank(username)) {
            throw new BusinessException("用户名为空!请重新输出");
        }
        TbUser user = this.baseMapper.selectOne(new QueryWrapper<TbUser>().eq("username", username));
        if (user == null) {
            return WrapMapper.ok();
        }
        return WrapMapper.wrap(500, "用户名已存在请重新输入!");
    }

    @Override
    public Wrapper register(UserDTO userDTO) {
        if (userDTO == null) {
            return WrapMapper.error("注册失败!");
        }
        if (!checkUsername(userDTO.getUsername()).success()) {
            throw new BusinessException(500, "该用户名已经存在请重新输入!");
        }
        TbUser tbUser = new TbUser();
        BeanUtil.copyProperties(userDTO, tbUser);
        tbUser.setCreated(LocalDateTime.now());
        tbUser.setUpdated(LocalDateTime.now());
        this.baseMapper.insert(tbUser);
        return WrapMapper.ok();
    }
}
