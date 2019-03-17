package com.lg.order.service;

import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.TbOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
public interface TbOrderService extends IService<TbOrder> {

    /**
     * 提交订单
     * @param tbOrder
     * @return
     */
    Wrapper submitOrder(TbOrder tbOrder);

    /**
     * 支付超时修改状态
     * @param orderId
     */
    void payTimeOut(Long orderId);

}
