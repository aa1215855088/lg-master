package com.lg.seckill.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.enums.OrderStautsEnum;
import com.lg.commons.util.id.SnowflakeIdWorker;
import com.lg.seckill.mapper.TbSeckillOrderMapper;
import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.model.domain.TbSeckillOrder;
import com.lg.seckill.service.TbSeckillGoodsService;
import com.lg.seckill.service.TbSeckillOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0", timeout = 6000)
@Transactional
public class TbSeckillOrderServiceImpl extends ServiceImpl<TbSeckillOrderMapper, TbSeckillOrder> implements TbSeckillOrderService {

    @Autowired
    private TbSeckillGoodsService tbSeckillGoodsService;

    @Override
    public Integer createOrder(Long seckillId, String username, Long id) {
        TbSeckillGoods seckillGoods = this.tbSeckillGoodsService.selectById(seckillId);
        TbSeckillOrder tbSeckillOrder = new TbSeckillOrder();
        tbSeckillOrder.setId(id);
        tbSeckillOrder.setSeckillId(seckillId);
        tbSeckillOrder.setCreateTime(LocalDateTime.now());
        tbSeckillOrder.setUserId(username);
        tbSeckillOrder.setSellerId(seckillGoods.getSellerId());
        tbSeckillOrder.setMoney(seckillGoods.getCostPrice());
        tbSeckillOrder.setStatus(OrderStautsEnum.PRE_PAYMENT.getCode());
//        tbSeckillOrder.setReceiverAddress()
        return this.baseMapper.insert(tbSeckillOrder);
    }
}
