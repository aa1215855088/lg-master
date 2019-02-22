package com.lg.product.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.exceptions.ProductBizException;
import com.lg.product.model.domain.TbItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbItemService extends IService<TbItem> {

    /**
     * 查询所有
     *
     * @return
     */
    Wrapper<List<TbItem>> findAll() throws ProductBizException;


    String hello();


}
