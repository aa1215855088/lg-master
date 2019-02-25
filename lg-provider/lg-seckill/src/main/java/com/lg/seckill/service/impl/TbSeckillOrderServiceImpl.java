package com.lg.seckill.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.seckill.mapper.TbSeckillOrderMapper;
import com.lg.seckill.model.domain.TbSeckillOrder;
import com.lg.seckill.service.TbSeckillOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
@Transactional
public class TbSeckillOrderServiceImpl extends ServiceImpl<TbSeckillOrderMapper, TbSeckillOrder> implements TbSeckillOrderService {
	
}
