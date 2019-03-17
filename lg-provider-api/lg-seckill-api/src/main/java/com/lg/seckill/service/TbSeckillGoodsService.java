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

    /**
     * 获取今天秒杀商品详情
     * @return
     */
    List<TbSeckillGoods> findByTodaySeckill();

    /**
     * 查询商品库存
     * @param id
     * @return
     */
    Wrapper getStockById(long id);

    /**
     * 判断活动是否开始
     * @param id
     * @return
     */
    boolean checkStartSeckill(Long id);


    /**
     * 秒杀主要业务逻辑
     * @param id
     * @param name
     * @return
     */
    Wrapper startSeckill(Long id, String name);

    /**
     * 扣减库存
     * @param seckillId
     */
    Integer deductioOfInventory(Long seckillId);
}
