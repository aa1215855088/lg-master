package com.lg.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.seckill.model.domain.TbSeckillGoods;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbSeckillGoodsService extends IService<TbSeckillGoods> {


    /**
     * 查询所有秒杀的商品
     *
     * @return
     */
    Wrapper<List<TbSeckillGoods>> findAll();
}
