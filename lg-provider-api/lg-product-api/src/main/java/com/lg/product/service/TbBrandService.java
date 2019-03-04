package com.lg.product.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbBrand;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface
TbBrandService extends IService<TbBrand> {

    /**
     * 添加商品品牌
     *
     * @param brand
     * @return
     */
    Wrapper save(TbBrand brand) throws Exception;
}
