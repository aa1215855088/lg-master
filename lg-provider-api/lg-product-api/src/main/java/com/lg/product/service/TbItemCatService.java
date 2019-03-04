package com.lg.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lg.commons.base.vo.PageVO;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.lg.product.model.domain.TbItemCat;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品类目 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbItemCatService extends IService<TbItemCat> {

    Wrapper<List<TbItemCat>> findAll();

}
