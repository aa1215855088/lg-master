package com.lg.seckill.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.seckill.mapper.TbSeckillGoodsMapper;
import com.lg.seckill.model.domain.TbSeckillGoods;
import com.lg.seckill.service.TbSeckillGoodsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service(version = "1.0.0")
@Transactional
public class TbSeckillGoodsServiceImpl extends ServiceImpl<TbSeckillGoodsMapper, TbSeckillGoods> implements TbSeckillGoodsService {
	
}
