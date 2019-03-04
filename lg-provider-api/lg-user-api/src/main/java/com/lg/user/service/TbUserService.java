package com.lg.user.service;

import com.lg.user.model.domain.TbUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbUserService extends IService<TbUser> {

    List<TbUser> findAll();

    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    TbUser findByLoginName(String username);
}
