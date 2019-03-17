package com.lg.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.user.model.domain.TbAddress;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbAddressService extends IService<TbAddress> {

    /**
     * 获取用户收货地址
     * @param name
     * @return
     */
    List<TbAddress> getUserAddress(String name);
}
