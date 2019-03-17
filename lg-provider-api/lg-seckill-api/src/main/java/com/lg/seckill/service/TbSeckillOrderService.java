package com.lg.seckill.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.lg.seckill.model.domain.TbSeckillOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */

public interface TbSeckillOrderService extends IService<TbSeckillOrder> {

    /**
     * 创建秒杀订单
     * @param seckillId
     * @param username
     */
    Integer createOrder(Long seckillId, String username,Long id);
}
