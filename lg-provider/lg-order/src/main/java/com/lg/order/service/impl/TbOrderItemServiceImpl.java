package com.lg.order.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.order.mapper.TbOrderItemMapper;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.order.service.TbOrderItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@Service
public class TbOrderItemServiceImpl extends ServiceImpl<TbOrderItemMapper, TbOrderItem> implements TbOrderItemService {
	
}
