package com.lg.order.web.controller;


import afu.org.checkerframework.checker.igj.qual.I;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.lg.commons.core.controller.BaseController;
import com.lg.commons.util.wrapper.WrapMapper;
import com.lg.commons.util.wrapper.Wrapper;
import com.lg.order.model.domain.Cart;
import com.lg.order.model.domain.TbOrder;
import com.lg.order.model.domain.TbOrderItem;
import com.lg.order.service.CartService;
import com.lg.order.service.TbOrderService;
import com.lg.user.model.domain.TbAddress;
import com.lg.user.service.TbAddressService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xuzilou
 * @since 2019-02-19
 */
@RestController
@RequestMapping("/order")
public class TbOrderController extends BaseController {

    @Reference(version = "1.0.0")
    private TbAddressService tbAddressService;
    @Reference(version = "1.0.0")
    private CartService cartService;
    @Reference(version = "1.0.0")
    private TbOrderService tbOrderService;

    @GetMapping("/createOrder")
    public ModelAndView createOrder(Long[] ids) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TbAddress> addressList = this.tbAddressService.getUserAddress(name);
        List<Cart> cartList = this.cartService.getRedisCartByUserAndIds(name, ids);
        if (CollUtil.isEmpty(cartList)) {
            return new ModelAndView("/order/cart");
        }
        logger.info("用户:{}创建订单:{}地址信息:{}订单信息:{}", name, Arrays.toString(ids), addressList, cartList);
        Map<String, Object> map = new HashMap<>();
        map.put("addressList", JSON.toJSONString(addressList));
        map.put("cartList", JSON.toJSONString(cartList));
        return new ModelAndView("getOrderInfo", map);
    }

    @PostMapping("/submitOrder")
    public Wrapper submitOrder(@RequestBody TbOrder tbOrder) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        tbOrder.setUserId(name);
        logger.info("获取订单信息:{}", tbOrder);
        return this.tbOrderService.submitOrder(tbOrder);
    }
}
