package com.lg.product.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.product.model.domain.TbGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.product.model.vo.GoodDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbGoodsService extends IService<TbGoods> {




     Wrapper save(GoodDto goods);

}
