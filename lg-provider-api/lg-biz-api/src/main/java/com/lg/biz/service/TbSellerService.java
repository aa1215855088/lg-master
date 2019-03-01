package com.lg.biz.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.biz.model.domain.TbSeller;
import com.lg.commons.util.wrapper.Wrapper;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbSellerService extends IService<TbSeller> {

    /**
     * 跟据商家登录名查询商家信息
     *
     * @param username
     * @return
     */
    TbSeller findByLoginName(String username);


    /**
     * 商家入驻
     * @param tbSeller
     * @return
     */
    Wrapper sellerInsert(TbSeller tbSeller);

    /**
     * 根据商家用户名查询商家
     * @param name
     * @return
     */
    Wrapper<TbSeller> findById(String name);


    /**
     * 修改商家资料
     * @param tbSeller
     * @return
     */
     Wrapper updateSellerInfo(TbSeller tbSeller);

    /**
     * 根据seller_id修改密码
     * @param tbSeller
     * @return
     */
    Wrapper updatePasswordById(TbSeller tbSeller);


}
