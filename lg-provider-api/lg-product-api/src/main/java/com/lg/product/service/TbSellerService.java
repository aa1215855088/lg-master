package com.lg.product.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.product.model.domain.TbSeller;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface
TbSellerService extends IService<TbSeller> {

    /**
     * 跟据商家登录名查询商家信息
     *
     * @param username
     * @return
     */
    TbSeller findByLoginName(String username);
}
