package com.lg.order.service.impl;

import java.math.BigDecimal;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lg.commons.base.constant.Constants;
import com.lg.commons.base.enums.OrderStautsEnum;
import com.lg.commons.base.exception.BusinessException;
import com.lg.commons.util.id.SnowflakeIdWorker;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.mapper.TbOrderMapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.order.service.CartService;
import com.lg.order.service.TbOrderItemService;
import com.lg.order.service.TbOrderService;
import com.lg.order.model.domain.TbOrder;
import com.lg.order.service.TbPayLogService;
import com.lg.product.model.domain.TbItem;
import com.lg.product.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
public class TbOrderServiceImpl extends ServiceImpl<TbOrderMapper, TbOrder> implements TbOrderService {

    @Reference(version = "1.0.0")
    private TbItemService itemService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private TbOrderItemService tbOrderItemService;
    @Autowired
    private CartService cartService;
    @Autowired
    private TbPayLogService tbPayLogService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Wrapper submitOrder(TbOrder tbOrder) {
        //判断商品库存是否够用
//        this.itemService.
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
        long orderId = snowflakeIdWorker.nextId();
        String orderItemStr = this.stringRedisTemplate.opsForValue().get("ORDERLIST:" + tbOrder.getUserId());
        List<Cart> carts = JSONArray.parseArray(orderItemStr, Cart.class);
        if (carts == null) {
            return WrapMapper.error("该订单已过期,请重新选择商品");
        }
        List<Long> ids = new ArrayList<>();
        carts.forEach(cart -> {
            cart.getOrderItemList().forEach(item -> {
                Wrapper msg = this.itemService.checkItemNumEnoughAndDecr(item.getItemId(), item.getNum());
                if (!msg.success()) {
                    throw new BusinessException(500, msg.getMessage());
                }
                item.setId(snowflakeIdWorker.nextId());
                item.setOrderId(orderId);
                this.tbOrderItemService.insert(item);
                ids.add(item.getItemId());
            });

        });
        tbOrder.setOrderId(orderId);
        tbOrder.setCreateTime(LocalDateTime.now());
        tbOrder.setUpdateTime(LocalDateTime.now());
        tbOrder.setStatus(OrderStautsEnum.PRE_PAYMENT.getCode());
        //设置订单超时时间
        tbOrder.setExpire(LocalDateTime.now().plusMinutes(Constants.ORDER_OUT_TIME));
        Integer row = this.baseMapper.insert(tbOrder);
        if (row != 1) {
            throw new BusinessException(500, "订单生成失败!");
        }
        this.stringRedisTemplate.opsForValue().set("ORDER:" + orderId, "666", 15, TimeUnit.MINUTES);
        //清除购物车
        Long[] longs = ArrayUtil.toArray(ids, Long.class);
        this.cartService.deleteCartToRedis(tbOrder.getUserId(), longs);
        return WrapMapper.ok();
    }

    @Override
    public void payTimeOut(Long orderId) {
        TbOrder tbOrder = this.baseMapper.selectById(orderId);
        tbOrder.setCloseTime(LocalDateTime.now());
        tbOrder.setStatus(OrderStautsEnum.PAY_FAIL.getCode());
        this.baseMapper.updateById(tbOrder);
    }
}
